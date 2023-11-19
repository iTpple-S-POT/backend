package org.com.itpple.spot.server.dto.oAuth;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserInfo {

  private String oAuthId;
  private String nickname;
  private String profileImage;
}
