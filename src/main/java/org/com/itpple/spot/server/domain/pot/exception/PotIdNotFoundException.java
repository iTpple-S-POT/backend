package org.com.itpple.spot.server.domain.pot.exception;

import org.com.itpple.spot.server.global.exception.CustomException;
import org.com.itpple.spot.server.global.exception.code.ErrorCode;

public class PotIdNotFoundException extends CustomException {

    public PotIdNotFoundException(String optionalMessage) {
        super(ErrorCode.NOT_FOUND_POT, optionalMessage);
    }
}
