package org.com.itpple.spot.server.domain.reaction.exception;

import org.com.itpple.spot.server.global.exception.CustomException;
import org.com.itpple.spot.server.global.exception.code.ErrorCode;

public class AddMultipleReactionException extends CustomException {

    public AddMultipleReactionException() {
        super(ErrorCode.NOT_ADD_MULTIPLE_REACTION);
    }
}
