package org.com.itpple.spot.server.domain.user.dto.request;

import org.com.itpple.spot.server.global.common.constant.Gender;
import org.com.itpple.spot.server.global.common.constant.Interest;
import org.com.itpple.spot.server.global.common.constant.Mbti;

import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.List;

public record UserInfoRequest(
        @Pattern(regexp = "^[a-zA-Z0-9가-힣_]{1,15}$", message = "공백없이 15자 이하로 작성해주세요 특수문자는 _만 사용 가능해요")
        String nickname,
        String phoneNumber,
        LocalDate birthDay,
        Gender gender,
        Mbti mbti,
        List<Interest> interests
){
}
