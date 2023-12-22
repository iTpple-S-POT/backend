package org.com.itpple.spot.server.exception;

import lombok.Getter;
import org.com.itpple.spot.server.exception.code.ErrorCode;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends RuntimeException {

    private final Integer code;
    private final String message;
    private final HttpStatus httpStatus;

    public CustomException(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.httpStatus = errorCode.getHttpStatus();
    }

    public CustomException(ErrorCode errorCode, String optionalMessage) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage() + " " + optionalMessage;
        this.httpStatus = errorCode.getHttpStatus();
    }
}
