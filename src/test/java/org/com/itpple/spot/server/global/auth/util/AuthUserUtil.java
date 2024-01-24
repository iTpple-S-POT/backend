package org.com.itpple.spot.server.global.auth.util;

import org.com.itpple.spot.server.domain.user.dto.UserDto;
import org.com.itpple.spot.server.domain.user.entity.User;
import org.com.itpple.spot.server.global.auth.userDetails.CustomUserDetails;
import org.com.itpple.spot.server.global.common.constant.Role;

public class AuthUserUtil {

    public static CustomUserDetails getCustomUserDetails() {

        return getCustomUserDetails(1L);
    }

    public static CustomUserDetails getCustomUserDetails(Long id) {

        return getCustomUserDetails(id, Role.USER);
    }

    public static CustomUserDetails getCustomUserDetails(Long id, Role role) {
        User user = User.builder().id(id).role(Role.USER).build();
        return CustomUserDetails.from(UserDto.from(user));
    }

}
