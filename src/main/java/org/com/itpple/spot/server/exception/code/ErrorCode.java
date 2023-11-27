package org.com.itpple.spot.server.exception.code;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 사용자 정의 에러 목록
 *
 * 1300 ~
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorCode {

    ;

    private final Integer code;
    private final String message;
    private final HttpStatus httpStatus;
}
