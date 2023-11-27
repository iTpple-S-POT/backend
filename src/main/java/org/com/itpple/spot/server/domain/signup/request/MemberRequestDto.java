package org.com.itpple.spot.server.domain.signup.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.com.itpple.spot.server.domain.member.entity.Member;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberRequestDto {
    private String nickname;
    private String phonenumber;
    private Date brith_day;
    private String gender;
    private String mbti;
    private List<String> interests = new ArrayList<>();



    public Member toMember() {
        return Member.builder()
                .nickname(nickname)
                .phonenumber(phonenumber)
                .birth_day(brith_day)
                .gender(gender)
                .mbti(mbti)
                .interests(interests)
                .build();
    }
}
