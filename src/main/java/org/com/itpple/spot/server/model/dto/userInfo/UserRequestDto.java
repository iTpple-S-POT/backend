package org.com.itpple.spot.server.model.dto.userInfo;

import lombok.*;
import org.com.itpple.spot.server.model.Gender;
import org.com.itpple.spot.server.model.entity.User;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequestDto {

    private Long memberId;

    @Pattern(regexp = "^[a-zA-Z0-9_]{1,15}$",
            message = "공백없이 5자 이하로 작성해주세요\n"+"특수문자는 _만 사용 가능해요")

    @NotNull(message = "닉네임은 필수 입력값입니다.")
    private String nickname;

    private String phoneNumber;
    private Date birthDay;
    private Gender gender;
    private String mbti;
    private List<String> interests = new ArrayList<>();

    public User toUser() {
        return User.builder()
                .memberId(memberId)
                .nickname(nickname)
                .phoneNumber(phoneNumber)
                .birthDay(birthDay)
                .gender(gender)
                .mbti(mbti)
                .interests(interests)
                .build();
    }
}
