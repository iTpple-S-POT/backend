package org.com.itpple.spot.server.service;

import org.com.itpple.spot.server.model.OAuthType;
import org.com.itpple.spot.server.model.dto.oAuth.UserInfo;

public interface OAuthService {

    OAuthType getOAuthType();

    String getSocialIdByToken(String accessToken, String refreshToken);

    UserInfo getUserInfoByToken(String accessToken, String refreshToken);
}
