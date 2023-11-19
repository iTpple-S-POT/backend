package org.com.itpple.spot.server.service;

import org.com.itpple.spot.server.model.OAuthType;

public interface OAuthServiceFactory {

  OAuthService getOAuthService(OAuthType oAuthType);
}
