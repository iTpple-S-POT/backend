package org.com.itpple.spot.server.dto.oAuth.kakao;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class KakaoAccount {

  private Profile profile;
  private String gender;
  private String birthday;
  private String email;
}
