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

    public static Payload of(Long userId) {
        return new Payload(userId);
    }
}
