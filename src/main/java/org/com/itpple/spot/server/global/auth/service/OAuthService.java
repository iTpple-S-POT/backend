package org.com.itpple.spot.server.global.auth.service;

import org.com.itpple.spot.server.global.common.constant.OAuthType;
import org.com.itpple.spot.server.global.auth.dto.UserInfo;

public interface OAuthService {

    OAuthType getOAuthType();

    String getSocialIdByToken(String accessToken, String refreshToken);

    UserInfo getUserInfoByToken(String accessToken, String refreshToken);
}
