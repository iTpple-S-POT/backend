package org.com.itpple.spot.server.global.auth.service;

import java.util.Optional;
import org.com.itpple.spot.server.domain.user.dto.UserDto;
import org.com.itpple.spot.server.global.auth.dto.TokenResponse;
import org.com.itpple.spot.server.global.common.constant.OAuthType;

public interface AuthService {

    Optional<UserDto> findBySocialId(String socialId);

    UserDto join(UserDto userDto);

    TokenResponse loginWithOAuth(OAuthType oAuthType, String accessToken);

    TokenResponse refresh(String refreshToken);

    void logout(Long userId);
}
