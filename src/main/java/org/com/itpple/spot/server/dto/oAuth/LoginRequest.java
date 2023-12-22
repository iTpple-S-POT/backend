package org.com.itpple.spot.server.dto.oAuth;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequest {

    private String accessToken;
    private String refreshToken;
}
