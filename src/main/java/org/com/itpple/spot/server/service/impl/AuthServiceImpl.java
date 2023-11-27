package org.com.itpple.spot.server.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.com.itpple.spot.server.common.jwt.TokenProvider;
import org.com.itpple.spot.server.model.OAuthType;
import org.com.itpple.spot.server.model.Role;
import org.com.itpple.spot.server.model.dto.oAuth.TokenResponse;
import org.com.itpple.spot.server.model.entity.User;
import org.com.itpple.spot.server.repository.UserRepository;
import org.com.itpple.spot.server.service.AuthService;
import org.com.itpple.spot.server.service.OAuthServiceFactory;
import org.com.itpple.spot.server.service.TokenService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final OAuthServiceFactory oAuthServiceFactory;
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final TokenService tokenService;

    @Override
    @Transactional
    public TokenResponse loginWithOAuth(OAuthType oAuthType, String accessToken,
            String refreshToken) {
        var oAuthService = oAuthServiceFactory.getOAuthService(oAuthType);

        var socialId = oAuthService.getSocialIdByToken(accessToken, refreshToken);

        var user = userRepository.findBySocialId(socialId)
                .orElseGet(() -> {
                    var userInfo = oAuthService.getUserInfoByToken(accessToken, refreshToken);

                    var newUser = User.builder()
                            .socialId(socialId)
                            .role(Role.USER)
                            .name(userInfo.getNickname())
                            .profileImageUrl(userInfo.getProfileImage())
                            .build();

                    return userRepository.save(newUser);
                });

        var authentication = this.generateAuthentication(user);

        var tokenResponse = tokenProvider.generateToken(user.getId(),
                authentication);
        var newRefreshToken = tokenResponse.getRefreshToken();

        this.tokenService.saveRefreshToken(user.getId(), newRefreshToken);

        return tokenResponse;
    }


    @Override
    public TokenResponse refresh(String refreshToken) {
        if (!this.tokenProvider.validateRefreshToken(refreshToken)) {
            throw new RuntimeException("Refresh Token is not valid");
        }

        var userId = this.tokenProvider.getUserIdFromRefreshToken(refreshToken);

        if (this.tokenService.isRefreshTokenExist(userId, refreshToken)) {
            throw new RuntimeException("Refresh Token is not valid");
        }

        var user = userRepository.findById(userId);
        var authentication = this.generateAuthentication(user.get());

        var tokenResponse = tokenProvider.generateToken(userId, authentication);
        var newRefreshToken = tokenResponse.getRefreshToken();

        this.tokenService.saveRefreshToken(userId, newRefreshToken);

        return tokenResponse;
    }

    private Authentication generateAuthentication(User user) {
        return tokenProvider.generateAuthentication(user);
    }

    @Override
    public void logout(String accessToken) {
        if (!this.tokenProvider.validateAccessToken(accessToken)) {
            throw new RuntimeException("Access Token is not valid");
        }

        var userId = this.tokenProvider.getPayload(accessToken).get("userId", Long.class);

        this.tokenService.removeRefreshToken(userId);
    }


}
