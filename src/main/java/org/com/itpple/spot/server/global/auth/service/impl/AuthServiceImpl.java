package org.com.itpple.spot.server.global.auth.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.com.itpple.spot.server.domain.user.dto.UserDto;
import org.com.itpple.spot.server.domain.user.entity.User;
import org.com.itpple.spot.server.domain.user.repository.UserRepository;
import org.com.itpple.spot.server.global.auth.dto.TokenResponse;
import org.com.itpple.spot.server.global.auth.jwt.TokenProvider;
import org.com.itpple.spot.server.global.auth.service.AuthService;
import org.com.itpple.spot.server.global.auth.service.OAuthServiceFactory;
import org.com.itpple.spot.server.global.auth.service.TokenService;
import org.com.itpple.spot.server.global.auth.userDetails.CustomUserDetails;
import org.com.itpple.spot.server.global.common.constant.OAuthType;
import org.com.itpple.spot.server.global.common.constant.Role;
import org.com.itpple.spot.server.global.exception.BusinessException;
import org.com.itpple.spot.server.global.exception.code.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final OAuthServiceFactory oAuthServiceFactory;
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final TokenService tokenService;

    @Override
    @Transactional(readOnly = true)
    public Optional<UserDto> findBySocialId(String socialId) {
        return userRepository.findBySocialId(socialId)
                .map(UserDto::from);
    }

    @Override
    @Transactional
    public UserDto join(UserDto userDto) {
        User user = userRepository.save(userDto.toEntity());
        return UserDto.from(user);
    }

    @Override
    @Transactional
    public TokenResponse loginWithOAuth(OAuthType oAuthType, String accessToken) {
        var oAuthService = oAuthServiceFactory.getOAuthService(oAuthType);

        var socialId = oAuthService.getSocialIdByToken(accessToken);

        var user = userRepository.findBySocialId(socialId)
                .orElseGet(() -> {
                    var userInfo = oAuthService.getUserInfoByToken(accessToken);

                    var newUser = User.builder()
                            .socialId(socialId)
                            .role(Role.USER)
                            .name(userInfo.getNickname())
                            .profileImageUrl(userInfo.getProfileImage())
                            .build();

                    return userRepository.save(newUser);
                });
        var customUserDetails = CustomUserDetails.from(UserDto.from(user));

        var tokenResponse = tokenProvider.generateToken(customUserDetails);
        var newRefreshToken = tokenResponse.getRefreshToken();

        this.tokenService.saveRefreshToken(user.getId(), newRefreshToken);

        return tokenResponse;
    }


    @Override
    public TokenResponse refresh(String refreshToken) {
        if (!this.tokenProvider.validateRefreshToken(refreshToken)) {
            throw new BusinessException(ErrorCode.INVALID_REFRESH_TOKEN);
        }

        var userId = this.tokenProvider.getUserIdFromRefreshToken(refreshToken);

        if (!this.tokenService.isRefreshTokenExist(userId, refreshToken)) {
            throw new BusinessException(ErrorCode.NOT_FOUND_REFRESH_TOKEN);
        }

        var user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER));
        var customUserDetails = CustomUserDetails.from(UserDto.from(user));

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
