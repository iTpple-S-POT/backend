package org.com.itpple.spot.server.service.impl;

import java.net.URI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.com.itpple.spot.server.dto.oAuth.UserInfo;
import org.com.itpple.spot.server.dto.oAuth.kakao.KakaoInfo;
import org.com.itpple.spot.server.external.KakaoClient;
import org.com.itpple.spot.server.model.OAuthType;
import org.com.itpple.spot.server.service.OAuthService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoOAuthServiceImpl implements OAuthService {

  private static final OAuthType OAUTH_TYPE = OAuthType.KAKAO;
  private final KakaoClient kakaoClient;

  @Value("${oauth2.client.kakao.app-id}")
  private Integer APP_ID;

  @Value("${oauth2.client.kakao.user-info-uri}")
  private String USER_INFO_URI;

  @Value("${oauth2.client.kakao.token-info-uri}")
  private String TOKEN_INFO_URI;

  @Override
  public OAuthType getOAuthType() {
    return OAUTH_TYPE;
  }

  public String getOAuthIdByToken(String accessToken, String refreshToken) {
    var tokenInfo = kakaoClient.getTokenInfo(URI.create(TOKEN_INFO_URI), "Bearer " + accessToken);
    if (!APP_ID.equals(tokenInfo.getAppId())) {
      throw new RuntimeException("appId is not matched");
    }
    return this.generateOAuthId(tokenInfo.getId());
  }

  public UserInfo getUserInfoByToken(String accessToken, String refreshToken) {
    var kakaoInfo = kakaoClient.getInfo(URI.create(USER_INFO_URI), "Bearer " + accessToken);
    var oAuthId = this.generateOAuthId(kakaoInfo.getId());

    return KakaoInfo.newUserInfo(kakaoInfo, oAuthId);
  }

  public String generateOAuthId(Long id) {//카카오에서 주는 회원번호로 만들어야함
    return OAUTH_TYPE.getName() + "_" + id;
  }

}
