package org.com.itpple.spot.server.global.apple.dto;

import io.jsonwebtoken.Claims;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.com.itpple.spot.server.domain.user.dto.UserDto;
import org.com.itpple.spot.server.global.common.constant.OAuthType;
import org.com.itpple.spot.server.global.common.constant.Role;
import org.com.itpple.spot.server.global.common.constant.Status;

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

	public UserDto toUserDto(String name) {
		return UserDto.of(
				null,
				String.valueOf(OAuthType.APPLE),
				this.sub,
				Role.USER,
				name,
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				Status.PROGRESS
		);
	}
}
