package org.com.itpple.spot.server.util;

import org.com.itpple.spot.server.common.auth.userDetails.UserDetailsCustom;
import org.com.itpple.spot.server.constant.Role;
import org.com.itpple.spot.server.entity.User;

public class AuthUserUtil {

    public static UserDetailsCustom getUserDetailsCustom() {

        return getUserDetailsCustom(1L);
    }

    public static UserDetailsCustom getUserDetailsCustom(Long id) {

        return getUserDetailsCustom(id, Role.USER);
    }

    public static UserDetailsCustom getUserDetailsCustom(Long id, Role role) {

        return UserDetailsCustom.from(User.builder().id(id).role(Role.USER).build());
    }

}
