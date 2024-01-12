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
    // Auth 관련
    INVALID_REFRESH_TOKEN(1301, "리프레시 토큰이 유효하지 않습니다.", HttpStatus.BAD_REQUEST),
    NOT_FOUND_REFRESH_TOKEN(1302, "리프레시 토큰을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),

    // User 관련
    NOT_FOUND_USER(1401, "사용자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    MEMBER_ID_ALREADY_EXISTS(1402,"이미 존재하는 meberId 입니다", HttpStatus.BAD_REQUEST),
    NICKNAME_DUPLICATE(1403,"이미 존재하는 닉네임 입니다",HttpStatus.INTERNAL_SERVER_ERROR),
    NICKNAME_VALIDATION(1404,"공백없이 15자 이하로 작성해주세요 특수문자는 _만 사용 가능해요",HttpStatus.BAD_REQUEST),

    // File 관련
    ILLEGAL_FILE_NAME(1501, "파일 이름이 올바르지 않습니다.", HttpStatus.BAD_REQUEST),
    INVALID_FILE_KEY(1502, "파일 키에 해당하는 파일을 찾지 못했습니다", HttpStatus.BAD_REQUEST),

    // OAuth 관련
    NOT_MATCH_APP_ID(1601, "앱 아이디가 일치하지 않습니다.", HttpStatus.BAD_REQUEST),

    // Pot 관련
    NOT_FOUND_POT(1701, "팟을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    NOT_FOUND_CATEGORY(1702, "카테고리를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),

    // Reaction 관련
    NOT_ADD_MULTIPLE_REACTION(1801, "팟에 여러 개의 반응을 추가할 수 없습니다.", HttpStatus.BAD_REQUEST),

    // Comment 관련
    NOT_FOUND_PARENT_COMMENT(1901, "답글 추가하기 위한 댓글이 없습니다.", HttpStatus.NOT_FOUND),
    COMMENT_POT_NOT_MATCH(1902, "추가하려는 답글과 댓글의 POT이 다릅니다.", HttpStatus.BAD_REQUEST),
    ;

    private final Integer code;
    private final String message;
    private final HttpStatus httpStatus;
}
