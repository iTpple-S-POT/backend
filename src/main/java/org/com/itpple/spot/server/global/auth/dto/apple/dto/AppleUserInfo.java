package org.com.itpple.spot.server.global.auth.dto.apple.dto;

import io.jsonwebtoken.Claims;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AppleUserInfo {

	private String sub;
	private String email;

	public static AppleUserInfo from(Claims claims) {
		return new AppleUserInfo(
				claims.getSubject(),
				claims.get("email", String.class)
		);
	}
}
