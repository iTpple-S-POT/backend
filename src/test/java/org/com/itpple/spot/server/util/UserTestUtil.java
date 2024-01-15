package org.com.itpple.spot.server.util;

import org.com.itpple.spot.server.domain.user.entity.User;

public class UserTestUtil {

	public static User createUser() {
		return User.builder().build();
	}
}
