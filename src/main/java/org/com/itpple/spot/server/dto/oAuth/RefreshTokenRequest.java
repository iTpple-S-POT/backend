package org.com.itpple.spot.server.dto.oAuth;

import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RefreshTokenRequest {

  @NotNull
  private String refreshToken;

}
