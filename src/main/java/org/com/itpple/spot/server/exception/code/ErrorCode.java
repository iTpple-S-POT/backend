package org.com.itpple.spot.server.exception.code;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 사용자 정의 에러 목록
 * <p>
 * 1300 ~
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorCode {
    INVALID_REFRESH_TOKEN(1301, "리프레시 토큰이 유효하지 않습니다.", HttpStatus.BAD_REQUEST),
    NOT_FOUND_REFRESH_TOKEN(1302, "리프레시 토큰을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),

    NOT_FOUND_USER(1401, "사용자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),

    ILLEGAL_FILE_NAME(1501, "파일 이름이 올바르지 않습니다.", HttpStatus.BAD_REQUEST),
    INVALID_FILE_KEY(1502, "파일 키에 해당하는 파일을 찾지 못했습니다", HttpStatus.BAD_REQUEST),

    ;

    private final Integer code;
    private final String message;
    private final HttpStatus httpStatus;
}
