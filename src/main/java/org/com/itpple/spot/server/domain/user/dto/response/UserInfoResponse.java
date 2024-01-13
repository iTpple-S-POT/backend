package org.com.itpple.spot.server.domain.user.dto.response;

import lombok.Builder;
import org.com.itpple.spot.server.global.common.constant.Gender;
import org.com.itpple.spot.server.global.common.constant.Interest;
import org.com.itpple.spot.server.global.common.constant.Mbti;
import org.com.itpple.spot.server.global.common.constant.Status;
import org.com.itpple.spot.server.domain.user.entity.User;

import java.time.LocalDate;
import java.util.List;

@Builder
public record UserInfoResponse(
        Long id,
        String loginType,
        String role,
        String profileImageUrl,
        String name,
        String nickname,
        String phoneNumber,
        LocalDate birthDay,
        Gender gender,
        Mbti mbti,
        List<Interest> interests,
        Status status
) {
    public static UserInfoResponse from(User user) {
        return UserInfoResponse.builder()
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
