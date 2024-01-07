package org.com.itpple.spot.server.exception.user;

import org.com.itpple.spot.server.exception.CustomException;
import org.com.itpple.spot.server.exception.code.ErrorCode;

public class UserIdNotFoundException extends CustomException {

    public UserIdNotFoundException(String optionalMessage) {
        super(ErrorCode.NOT_FOUND_USER, optionalMessage);
    }
}
