package org.com.itpple.spot.server.domain.reaction.exception;

import org.com.itpple.spot.server.global.exception.CustomException;
import org.com.itpple.spot.server.global.exception.code.ErrorCode;

public class ReactionUserNotMatchException extends CustomException {

	public ReactionUserNotMatchException() {
		super(ErrorCode.NOT_MATCH_REACTION_USER);
	}
}
