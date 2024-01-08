package org.com.itpple.spot.server.dto.userInfo.response;

import lombok.Builder;
import org.com.itpple.spot.server.constant.Gender;
import org.com.itpple.spot.server.constant.Interest;
import org.com.itpple.spot.server.constant.Mbti;
import org.com.itpple.spot.server.constant.Status;
import org.com.itpple.spot.server.entity.User;

import java.time.LocalDate;
import java.util.List;

@Builder
public record UserInfoResponse(
        Long id,
        String loginType,
        String socialId,
        String role,
        String name,
        String nickname,
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
                .socialId(user.getSocialId())
                .name(user.getName())
                .nickname(user.getNickname())
                .birthDay(user.getBirthDay())
                .gender(user.getGender())
                .mbti(user.getMbti())
                .interests(user.getInterests())
                .status(user.getStatus())
                .build();
    }
}
