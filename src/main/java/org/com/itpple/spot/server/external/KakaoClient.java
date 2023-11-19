package org.com.itpple.spot.server.external;

import java.net.URI;
import org.com.itpple.spot.server.config.FeignConfiguration;
import org.com.itpple.spot.server.dto.oAuth.kakao.KakaoInfo;
import org.com.itpple.spot.server.dto.oAuth.kakao.KakaoTokenInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "kakaoClient", configuration = FeignConfiguration.class)
@Component
public interface KakaoClient {

  @GetMapping
  KakaoInfo getInfo(URI baseUrl, @RequestHeader("Authorization") String accessToken);

  @GetMapping
  KakaoTokenInfo getTokenInfo(URI baseUrl, @RequestHeader("Authorization") String accessToken);

}