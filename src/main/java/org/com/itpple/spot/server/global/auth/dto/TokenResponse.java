package org.com.itpple.spot.server.global.auth.dto;


import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TokenResponse implements Serializable {

    private String accessToken;
    private String refreshToken;
}
