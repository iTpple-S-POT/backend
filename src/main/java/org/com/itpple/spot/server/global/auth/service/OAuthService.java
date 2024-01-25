package org.com.itpple.spot.server.global.auth.service;

import org.com.itpple.spot.server.global.auth.dto.UserInfo;
import org.com.itpple.spot.server.global.common.constant.OAuthType;

public interface OAuthService {

    OAuthType getOAuthType();

    String getSocialIdByToken(String accessToken);

    UserInfo getUserInfoByToken(String accessToken);
}
