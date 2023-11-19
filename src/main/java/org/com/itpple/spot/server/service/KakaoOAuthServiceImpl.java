package org.com.itpple.spot.server.service;

import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.com.itpple.spot.server.dto.oAuth.UserInfo;
import org.com.itpple.spot.server.dto.oAuth.kakao.KakaoInfo;
import org.com.itpple.spot.server.external.KakaoClient;
import org.com.itpple.spot.server.model.OAuthType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KakaoOAuthServiceImpl implements OAuthService {

  private static final OAuthType OAUTH_TYPE = OAuthType.KAKAO;
  private final KakaoClient kakaoClient;

  @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
  private String clientId;

  @Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
  private String clientSecret;

  @Value("${spring.security.oauth2.client.provider.kakao.user-info-uri}")
  private String userInfoUri;

  @Override
  public OAuthType getOAuthType() {
    return OAUTH_TYPE;
  }

  public String getOAuthIdByToken(String accessToken, String refreshToken) {
    var tokenInfo = kakaoClient.getTokenInfo(URI.create(userInfoUri), accessToken);
    return OAUTH_TYPE.getName() + "_" + tokenInfo.getId();
  }

  public UserInfo getUserInfoByToken(String accessToken, String refreshToken) {
    var kakaoInfo = kakaoClient.getInfo(URI.create(userInfoUri), accessToken);
    return KakaoInfo.newUserInfo(kakaoInfo);
  }

}
