package org.com.itpple.spot.server.global.auth.exception;

import org.com.itpple.spot.server.global.exception.BusinessException;
import org.com.itpple.spot.server.global.exception.code.ErrorCode;

public class TokenValidException extends BusinessException {

    public TokenValidException() {
        super(ErrorCode.TOKEN_NOT_VALID);
    }
}
