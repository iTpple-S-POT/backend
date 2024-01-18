package org.com.itpple.spot.server.domain.location.exception;

import org.com.itpple.spot.server.global.exception.CustomException;
import org.com.itpple.spot.server.global.exception.code.ErrorCode;

public class LocationIdNotFoundException extends CustomException {
    public LocationIdNotFoundException(String optionalMessage) {
        super(ErrorCode.NOT_FOUND_LOCATION, optionalMessage);
    }
}
