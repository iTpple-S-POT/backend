package org.com.itpple.spot.server.domain.signup.request;

import lombok.*;
import org.com.itpple.spot.server.domain.member.entity.Member;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberRequestDto {
    private String nickname;
    private String phoneNumber;
    private Date birthDay;
    private String gender;
    private String mbti;
    private List<String> interests = new ArrayList<>();

    public Member toMember() {
        return Member.builder()
                .nickname(nickname)
                .phoneNumber(phoneNumber)
                .birthDay(birthDay)
                .gender(gender)
                .mbti(mbti)
                .interests(interests)
                .build();
    }
}
