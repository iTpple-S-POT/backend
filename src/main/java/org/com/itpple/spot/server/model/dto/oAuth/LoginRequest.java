package org.com.itpple.spot.server.model.dto.oAuth;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequest {

    private String accessToken;
    private String refreshToken;
}
