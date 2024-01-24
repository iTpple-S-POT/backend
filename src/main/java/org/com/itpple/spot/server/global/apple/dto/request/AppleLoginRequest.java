package org.com.itpple.spot.server.global.apple.dto.request;

import javax.validation.constraints.NotBlank;

public record AppleLoginRequest(
		@NotBlank String identityToken,
		String name
) {
}
