package org.com.itpple.spot.server.dto;

import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class Payload {

    private Long userId;

    public static Payload fromClaims(Claims claims) {
        return new Payload(claims.get("userId", Long.class));
    }
}
