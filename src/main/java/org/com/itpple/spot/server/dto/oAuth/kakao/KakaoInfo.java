package org.com.itpple.spot.server.dto.oAuth.kakao;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.com.itpple.spot.server.dto.oAuth.UserInfo;

@ToString
@Getter
@NoArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class KakaoInfo {

  private KakaoAccount kakaoAccount;

  public static UserInfo newUserInfo(KakaoInfo kakaoInfo) {
    return UserInfo.builder()
        .nickname(kakaoInfo.getKakaoAccount().getProfile().getNickname())
        .build();
  }
}
