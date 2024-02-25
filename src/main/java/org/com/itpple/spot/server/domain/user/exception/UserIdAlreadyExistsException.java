package org.com.itpple.spot.server.domain.user.exception;

import org.com.itpple.spot.server.global.exception.BusinessException;
import org.com.itpple.spot.server.global.exception.code.ErrorCode;

public class UserIdAlreadyExistsException extends BusinessException {
    public UserIdAlreadyExistsException() {
        super(ErrorCode.USER_ID_ALREADY_EXISTS);
    }
}
