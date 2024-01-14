package org.com.itpple.spot.server.global.auth.service;

import org.com.itpple.spot.server.global.common.constant.OAuthType;

public interface OAuthServiceFactory {

    OAuthService getOAuthService(OAuthType oAuthType);
}
