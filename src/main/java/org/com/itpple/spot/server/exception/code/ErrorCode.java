package org.com.itpple.spot.server.exception.code;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorCode {

    ;

    private final Integer code;
    private final String message;
}
