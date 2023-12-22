package org.com.itpple.spot.server.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.com.itpple.spot.server.constant.OAuthType;
import org.com.itpple.spot.server.dto.oAuth.UserInfo;
import org.com.itpple.spot.server.dto.oAuth.kakao.KakaoInfo;
import org.com.itpple.spot.server.exception.CustomException;
import org.com.itpple.spot.server.exception.code.ErrorCode;
import org.com.itpple.spot.server.external.KakaoClient;
import org.com.itpple.spot.server.service.OAuthService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoOAuthServiceImpl implements OAuthService {

    private static final OAuthType OAUTH_TYPE = OAuthType.KAKAO;
    private final KakaoClient kakaoClient;
    private static final String HEADER_PREFIX = "Bearer ";


    @Value("${oauth2.client.kakao.appId}")
    private Integer APP_ID;

    @Override
    public OAuthType getOAuthType() {
        return OAUTH_TYPE;
    }

    public String getSocialIdByToken(String accessToken, String refreshToken) {
        var tokenInfo = kakaoClient.getTokenInfo(HEADER_PREFIX + accessToken);
        if (!APP_ID.equals(tokenInfo.getAppId())) {
            throw new CustomException(ErrorCode.NOT_MATCH_APP_ID);
        }
        return this.generateSocialId(tokenInfo.getId());
    }

    public UserInfo getUserInfoByToken(String accessToken, String refreshToken) {
        var kakaoInfo = kakaoClient.getInfo(HEADER_PREFIX + accessToken);
        var socialId = this.generateSocialId(kakaoInfo.getId());

        return KakaoInfo.newUserInfo(kakaoInfo, socialId);
    }

    private String generateSocialId(Long id) {//카카오에서 주는 회원번호로 만들어야함
        return OAUTH_TYPE.getName() + "_" + id;
    }

}
