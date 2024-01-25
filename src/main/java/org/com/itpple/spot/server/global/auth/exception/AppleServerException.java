package org.com.itpple.spot.server.global.auth.exception;

import org.com.itpple.spot.server.global.exception.CustomException;
import org.com.itpple.spot.server.global.exception.code.ErrorCode;

public class AppleServerException extends CustomException {

	public AppleServerException() {
		super(ErrorCode.APPLE_SERVER_ERROR);
	}
}
