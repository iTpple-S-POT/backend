package org.com.itpple.spot.server.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.com.itpple.spot.server.common.auth.jwt.TokenProvider;
import org.com.itpple.spot.server.common.auth.userDetails.CustomUserDetails;
import org.com.itpple.spot.server.constant.OAuthType;
import org.com.itpple.spot.server.constant.Role;
import org.com.itpple.spot.server.dto.oAuth.TokenResponse;
import org.com.itpple.spot.server.entity.User;
import org.com.itpple.spot.server.exception.CustomException;
import org.com.itpple.spot.server.exception.code.ErrorCode;
import org.com.itpple.spot.server.repository.UserRepository;
import org.com.itpple.spot.server.service.AuthService;
import org.com.itpple.spot.server.service.OAuthServiceFactory;
import org.com.itpple.spot.server.service.TokenService;
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
        var customUserDetails = CustomUserDetails.from(user);

        var tokenResponse = tokenProvider.generateToken(customUserDetails);
        var newRefreshToken = tokenResponse.getRefreshToken();

        this.tokenService.saveRefreshToken(user.getId(), newRefreshToken);

        return tokenResponse;
    }


    @Override
    public TokenResponse refresh(String refreshToken) {
        if (!this.tokenProvider.validateRefreshToken(refreshToken)) {
            throw new CustomException(ErrorCode.INVALID_REFRESH_TOKEN);
        }

        var userId = this.tokenProvider.getUserIdFromRefreshToken(refreshToken);

        if (!this.tokenService.isRefreshTokenExist(userId, refreshToken)) {
            throw new CustomException(ErrorCode.NOT_FOUND_REFRESH_TOKEN);
        }

        var user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        var customUserDetails = CustomUserDetails.from(user);

        var tokenResponse = tokenProvider.generateToken(customUserDetails);
        var newRefreshToken = tokenResponse.getRefreshToken();

        this.tokenService.saveRefreshToken(userId, newRefreshToken);

        return tokenResponse;
    }

    @Override
    public void logout(Long userId) {
        this.tokenService.removeRefreshToken(userId);
    }


}
