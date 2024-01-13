package org.com.itpple.spot.server.domain.user.exception;

import org.com.itpple.spot.server.global.exception.CustomException;
import org.com.itpple.spot.server.global.exception.code.ErrorCode;

public class UserIdAlreadyExistsException extends CustomException {
    public UserIdAlreadyExistsException() {
        super(ErrorCode.MEMBER_ID_ALREADY_EXISTS);
    }
}
