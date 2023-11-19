package org.com.itpple.spot.server.service;

import org.com.itpple.spot.server.dto.oAuth.UserInfo;
import org.com.itpple.spot.server.model.OAuthType;

public interface OAuthService {

  OAuthType getOAuthType();

  String getOAuthIdByToken(String accessToken, String refreshToken);

  UserInfo getUserInfoByToken(String accessToken, String refreshToken);
}
