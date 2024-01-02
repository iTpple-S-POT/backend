package org.com.itpple.spot.server.exception.pot;

import org.com.itpple.spot.server.exception.CustomException;
import org.com.itpple.spot.server.exception.code.ErrorCode;

public class PotIdNotFoundException extends CustomException {

    public PotIdNotFoundException(String optionalMessage) {
        super(ErrorCode.NOT_FOUND_POT, optionalMessage);
    }
}
