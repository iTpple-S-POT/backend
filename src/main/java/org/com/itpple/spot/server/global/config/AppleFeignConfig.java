package org.com.itpple.spot.server.global.config;

import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import org.com.itpple.spot.server.global.external.apple.AppleFeignError;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@Configuration
public class AppleFeignConfig {

	@Bean
	public RequestInterceptor appleRequestInterceptor() {
		return template -> template.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
	}

	@Bean
	public ErrorDecoder appleErrorDecoder() {
		return new AppleFeignError();
	}
}
