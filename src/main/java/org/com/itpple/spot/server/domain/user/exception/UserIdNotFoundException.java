package org.com.itpple.spot.server.domain.user.exception;

import org.com.itpple.spot.server.global.exception.CustomException;
import org.com.itpple.spot.server.global.exception.code.ErrorCode;

public class UserIdNotFoundException extends CustomException {

    public UserIdNotFoundException(String optionalMessage) {
        super(ErrorCode.NOT_FOUND_USER, optionalMessage);
    }
}
