package org.com.itpple.spot.server.dto.userInfo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.com.itpple.spot.server.constant.Gender;
import org.com.itpple.spot.server.constant.Interest;
import org.com.itpple.spot.server.constant.Mbti;
import org.com.itpple.spot.server.constant.NickNameData;
import org.com.itpple.spot.server.entity.User;

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
                ] +
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
