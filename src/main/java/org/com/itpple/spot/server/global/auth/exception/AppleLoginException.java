package org.com.itpple.spot.server.global.auth.exception;

import org.com.itpple.spot.server.global.exception.BusinessException;
import org.com.itpple.spot.server.global.exception.code.ErrorCode;

public class AppleLoginException extends BusinessException {

    public AppleLoginException() {
        super(ErrorCode.APPLE_LOGIN_ERROR);
    }
}
