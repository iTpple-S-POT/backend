package org.com.itpple.spot.server.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorCode {

    UNHANDLED(1000, "알 수 없는 서버 에러가 발생했습니다."),

    METHOD_ARGUMENT_NOT_VALID(1100, "데이터 유효성 검사에 실패했습니다."),
    CONSTRAINT_VIOLATION(1101, "데이터 유효성 검사에 실패했습니다.")
    ;

    private final Integer code;
    private final String message;
}
