package org.com.itpple.spot.server.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.com.itpple.spot.server.domain.user.entity.User;
import org.com.itpple.spot.server.global.common.constant.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Builder
@Getter
public class UserInfoDto {
    private final Long id;
    private final OAuthType loginType;
    private final String role;
    private final String profileImageUrl;
    private final String name;
    private final String nickname;
    private final String phoneNumber;
    private final LocalDate birthDay;
    private final Gender gender;
    private final Mbti mbti;
    private final List<Interest> interests;
    private final Status status;
    public static UserInfoDto from(User user) {
        return UserInfoDto.builder()
                .id(user.getId())
                .loginType(user.getLoginType())
                .profileImageUrl(user.getProfileImageUrl())
                .name(user.getName())
                .nickname(user.getNickname())
                .phoneNumber(user.getPhoneNumber())
                .birthDay(user.getBirthDay())
                .gender(user.getGender())
                .mbti(user.getMbti())
                .interests(user.getInterests())
                .status(user.getStatus())
                .build();
    }
}
