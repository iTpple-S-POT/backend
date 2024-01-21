package org.com.itpple.spot.server.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.com.itpple.spot.server.domain.user.entity.User;
import org.com.itpple.spot.server.global.common.constant.Gender;
import org.com.itpple.spot.server.global.common.constant.Interest;
import org.com.itpple.spot.server.global.common.constant.Mbti;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Builder
@Getter
public class UserProfileDto {
    private final Long id;
    private final String loginType;
    private final String profileImageUrl;
    private final String name;
    private final String nickname;
    private final LocalDate birthDay;
    private final Gender gender;
    private final Mbti mbti;
    private final List<Interest> interests;
    public static UserProfileDto from(User user) {
        return UserProfileDto.builder()
                .id(user.getId())
                .loginType(user.getLoginType())
                .profileImageUrl(user.getProfileImageUrl())
                .name(user.getName())
                .nickname(user.getNickname())
                .birthDay(user.getBirthDay())
                .gender(user.getGender())
                .mbti(user.getMbti())
                .interests(user.getInterests())
                .build();
    }

}
