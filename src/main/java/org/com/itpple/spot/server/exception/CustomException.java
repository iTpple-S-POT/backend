package org.com.itpple.spot.server.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class CustomException extends RuntimeException {

    private final HttpStatus httpStatus;
    private final Integer code;
    private final String message;

    public CustomException(HttpStatus httpStatus, ErrorCode errorCode) {
        this.httpStatus = httpStatus;
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    public CustomException(HttpStatus httpStatus, ErrorCode errorCode, String optionalMessage) {
        this.httpStatus = httpStatus;
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage() + " " + optionalMessage;
    }
}
