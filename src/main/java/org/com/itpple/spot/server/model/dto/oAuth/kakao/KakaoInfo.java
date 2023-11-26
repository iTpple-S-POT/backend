package org.com.itpple.spot.server.model.dto.oAuth.kakao;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.com.itpple.spot.server.model.dto.oAuth.UserInfo;

@ToString
@Getter
@NoArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class KakaoInfo {

    private Long id;

    private KakaoAccount kakaoAccount;

    public static UserInfo newUserInfo(KakaoInfo kakaoInfo, String oAuthId) {
        return UserInfo.builder()
                .oAuthId(oAuthId)
                .nickname(kakaoInfo.getKakaoAccount().getProfile().getNickname())
                .profileImage(kakaoInfo.getKakaoAccount().getProfile().getProfileImageUrl())
                .build();
    }
}