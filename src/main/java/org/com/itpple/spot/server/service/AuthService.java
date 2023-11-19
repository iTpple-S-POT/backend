package org.com.itpple.spot.server.service;

import org.com.itpple.spot.server.dto.oAuth.TokenResponse;
import org.com.itpple.spot.server.model.OAuthType;

public interface AuthService {

  TokenResponse loginWithOAuth(OAuthType oAuthType, String accessToken, String refreshToken);

  TokenResponse refresh(String refreshToken);

  void logout(String accessToken);
}
