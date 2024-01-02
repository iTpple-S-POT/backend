package org.com.itpple.spot.server.exception.reaction;

import org.com.itpple.spot.server.exception.CustomException;
import org.com.itpple.spot.server.exception.code.ErrorCode;

public class AddMultipleReactionException extends CustomException {

    public AddMultipleReactionException() {
        super(ErrorCode.NOT_ADD_MULTIPLE_REACTION);
    }
}
