package org.com.itpple.spot.server.model.dto.userInfo;

import lombok.*;
import org.com.itpple.spot.server.model.Gender;
import org.com.itpple.spot.server.model.Interest;
import org.com.itpple.spot.server.model.Mbti;
import org.com.itpple.spot.server.model.entity.NickNameData;
import org.com.itpple.spot.server.model.entity.User;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequestDto {

    @Pattern(regexp = "^[a-zA-Z0-9가-힣_]{1,15}$", message = "공백없이 15자 이하로 작성해주세요 특수문자는 _만 사용 가능해요")
    private String nickname;

    private String phoneNumber;
    private LocalDate birthDay;
    private Gender gender;
    private Mbti mbti;
    private List<Interest> interests = new ArrayList<>();
    private String generateDefaultNickname() {
        Random random = new Random();
        int randomNumbers = random.nextInt(100000);
        return NickNameData.determiners[
                (int) Math.floor(Math.random() * NickNameData.determiners.length)
                ]  +
                NickNameData.animals[
                        (int) Math.floor(Math.random() * NickNameData.animals.length)
                        ] +
                randomNumbers;
    }
    public User toUser() {
        if (nickname == null || nickname.isEmpty()) {
            String defaultNickname = generateDefaultNickname();
            nickname = defaultNickname;
        }
        return User.builder()
                .nickname(nickname)
                .phoneNumber(phoneNumber)
                .birthDay(birthDay)
                .gender(gender)
                .mbti(mbti)
                .interests(interests)
                .build();
    }
}
