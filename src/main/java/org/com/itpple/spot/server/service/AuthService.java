package org.com.itpple.spot.server.service;

import org.com.itpple.spot.server.constant.OAuthType;
import org.com.itpple.spot.server.dto.oAuth.TokenResponse;

public interface AuthService {

    TokenResponse loginWithOAuth(OAuthType oAuthType, String accessToken, String refreshToken);

    TokenResponse refresh(String refreshToken);

    void logout(Long userId);
}
