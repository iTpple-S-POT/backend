package org.com.itpple.spot.server.global.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserInfo {

    private String socialId;
    private String nickname;
    private String profileImage;
}
