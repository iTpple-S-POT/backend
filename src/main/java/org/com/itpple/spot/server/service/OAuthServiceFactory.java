package org.com.itpple.spot.server.service;

import org.com.itpple.spot.server.constant.OAuthType;

public interface OAuthServiceFactory {

    OAuthService getOAuthService(OAuthType oAuthType);
}
