package org.com.itpple.spot.server.global.auth.service;

import org.com.itpple.spot.server.domain.user.dto.UserDto;
import org.com.itpple.spot.server.global.common.constant.OAuthType;
import org.com.itpple.spot.server.global.auth.dto.TokenResponse;

import java.util.Optional;

public interface AuthService {

    Optional<UserDto> findBySocialId(String socialId);

    UserDto join(UserDto userDto);

    TokenResponse loginWithOAuth(OAuthType oAuthType, String accessToken, String refreshToken);

    TokenResponse refresh(String refreshToken);

    void logout(Long userId);
}
