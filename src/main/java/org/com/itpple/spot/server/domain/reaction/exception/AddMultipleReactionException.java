package org.com.itpple.spot.server.domain.reaction.exception;

import org.com.itpple.spot.server.global.exception.BusinessException;
import org.com.itpple.spot.server.global.exception.code.ErrorCode;

public class AddMultipleReactionException extends BusinessException {

    public AddMultipleReactionException() {
        super(ErrorCode.NOT_ADD_MULTIPLE_REACTION);
    }
}
