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

    MEMBER_ID_ALREADY_EXISTS(400,"이미 존재하는 meberId 입니다", HttpStatus.BAD_REQUEST),
    NICKNAME_DUPLICATE(500,"이미 존재하는 닉네임 입니다",HttpStatus.INTERNAL_SERVER_ERROR),
    NICKNAME_VALIDATION(400,"공백없이 15자 이하로 작성해주세요 특수문자는 _만 사용 가능해요",HttpStatus.BAD_REQUEST);
    private final Integer code;
    private final String message;
    private final HttpStatus httpStatus;
}
