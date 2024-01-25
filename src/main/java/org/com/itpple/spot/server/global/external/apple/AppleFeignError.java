package org.com.itpple.spot.server.global.external.apple;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.com.itpple.spot.server.global.auth.exception.AppleServerException;

public class AppleFeignError implements ErrorDecoder {

	@Override
	public Exception decode(String methodKey, Response response) {
		throw new AppleServerException();
	}
}
