package org.com.itpple.spot.server.external;

import org.com.itpple.spot.server.model.dto.oAuth.kakao.KakaoInfo;
import org.com.itpple.spot.server.model.dto.oAuth.kakao.KakaoTokenInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "kakaoClient", url = "https://kapi.kakao.com")
@Component
public interface KakaoClient {

    @GetMapping(value = "/v2/user/me", consumes = MediaType.APPLICATION_JSON_VALUE)
    KakaoInfo getInfo(@RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken);

    @GetMapping(value = "/v1/user/access_token_info", consumes = MediaType.APPLICATION_JSON_VALUE)
    KakaoTokenInfo getTokenInfo(@RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken);

}