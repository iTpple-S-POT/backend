package org.com.itpple.spot.server.service;

import org.com.itpple.spot.server.model.OAuthType;
import org.com.itpple.spot.server.model.dto.oAuth.TokenResponse;

public interface AuthService {

    TokenResponse loginWithOAuth(OAuthType oAuthType, String accessToken, String refreshToken);

    TokenResponse refresh(String refreshToken);

    void logout(Long userId);
}
