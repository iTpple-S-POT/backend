package org.com.itpple.spot.server.global.external.apple;

import org.com.itpple.spot.server.global.auth.dto.apple.dto.ApplePublicKey;
import org.com.itpple.spot.server.global.config.AppleFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(
		name = "apple-api",
		url = "https://appleid.apple.com",
		configuration = AppleFeignConfig.class
)
public interface AppleApiClient {

	@GetMapping("/auth/keys")
	ApplePublicKey getApplePublicKey();
}
