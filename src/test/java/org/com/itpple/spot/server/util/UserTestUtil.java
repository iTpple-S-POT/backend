package org.com.itpple.spot.server.util;

import org.com.itpple.spot.server.domain.user.entity.User;

public class UserTestUtil {

	public static User create() {
		return User.builder()
				.profileImageUrl("profileImageUrl")
				.name("name")
				.build();
	}
}
