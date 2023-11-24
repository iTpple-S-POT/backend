package org.com.itpple.spot.server.exception.common;

import org.com.itpple.spot.server.exception.CustomException;
import org.com.itpple.spot.server.exception.code.ErrorCode;
import org.springframework.http.HttpStatus;

public abstract class NotFoundException extends CustomException {

    public NotFoundException(ErrorCode errorCode) {
        super(HttpStatus.NOT_FOUND, errorCode);
    }

    public NotFoundException(ErrorCode errorCode, String optionalMessage) {
        super(HttpStatus.NOT_FOUND, errorCode, optionalMessage);
    }
}
