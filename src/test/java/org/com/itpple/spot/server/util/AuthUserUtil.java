package org.com.itpple.spot.server.util;

import org.com.itpple.spot.server.global.auth.userDetails.CustomUserDetails;
import org.com.itpple.spot.server.global.common.constant.Role;
import org.com.itpple.spot.server.domain.user.entity.User;

public class AuthUserUtil {

    public static CustomUserDetails getCustomUserDetails() {

        return getCustomUserDetails(1L);
    }

    public static CustomUserDetails getCustomUserDetails(Long id) {

        return getCustomUserDetails(id, Role.USER);
    }

    public static CustomUserDetails getCustomUserDetails(Long id, Role role) {

        return CustomUserDetails.from(User.builder().id(id).role(Role.USER).build());
    }

}
