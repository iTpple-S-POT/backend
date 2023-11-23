package org.com.itpple.spot.server.exception.common;

import org.com.itpple.spot.server.exception.CustomException;
import org.com.itpple.spot.server.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public abstract class ConflictException extends CustomException {

    public ConflictException(ErrorCode errorCode) {
        super(HttpStatus.CONFLICT, errorCode);
    }

    public ConflictException(ErrorCode errorCode, String optionalMessage) {
        super(HttpStatus.CONFLICT, errorCode, optionalMessage);
    }
}
