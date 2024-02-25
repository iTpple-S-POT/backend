package org.com.itpple.spot.server.domain.pot.exception;

import org.com.itpple.spot.server.global.exception.BusinessException;
import org.com.itpple.spot.server.global.exception.code.ErrorCode;

public class PotIdNotFoundException extends BusinessException {

    public PotIdNotFoundException(String optionalMessage) {
        super(ErrorCode.NOT_FOUND_POT, optionalMessage);
    }
}
