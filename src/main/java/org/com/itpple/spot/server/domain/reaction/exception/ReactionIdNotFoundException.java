package org.com.itpple.spot.server.domain.reaction.exception;

import org.com.itpple.spot.server.global.exception.BusinessException;
import org.com.itpple.spot.server.global.exception.code.ErrorCode;

public class ReactionIdNotFoundException extends BusinessException {

    public ReactionIdNotFoundException(String optionalMessage) {
        super(ErrorCode.NOT_FOUND_REACTION, optionalMessage);
    }
}
