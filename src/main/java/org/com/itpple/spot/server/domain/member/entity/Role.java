package org.com.itpple.spot.server.domain.member.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
    ROLE_USER("회원"),
    ROLE_GUEST("게스트"),
    ROLE_ADMIN("관리자");

    private final String roleName;
}
