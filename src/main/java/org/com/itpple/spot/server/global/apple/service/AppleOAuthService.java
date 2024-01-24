package org.com.itpple.spot.server.global.apple.service;

import org.com.itpple.spot.server.global.apple.dto.AppleUserInfo;

public interface AppleOAuthService {

	AppleUserInfo getUserInfo(String identityToken);
}
