package org.com.itpple.spot.server.global.auth.service;

import org.com.itpple.spot.server.global.common.constant.OAuthType;
import org.com.itpple.spot.server.global.auth.dto.TokenResponse;

public interface AuthService {

    TokenResponse loginWithOAuth(OAuthType oAuthType, String accessToken, String refreshToken);

    TokenResponse refresh(String refreshToken);

    void logout(Long userId);
}
