package org.com.itpple.spot.server.service;

import org.com.itpple.spot.server.constant.OAuthType;
import org.com.itpple.spot.server.dto.oAuth.UserInfo;

public interface OAuthService {

    OAuthType getOAuthType();

    String getSocialIdByToken(String accessToken, String refreshToken);

    UserInfo getUserInfoByToken(String accessToken, String refreshToken);
}
