package org.com.itpple.spot.server.service.impl;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.com.itpple.spot.server.core.jwt.TokenProvider;
import org.com.itpple.spot.server.dto.oAuth.TokenResponse;
import org.com.itpple.spot.server.entity.User;
import org.com.itpple.spot.server.model.OAuthType;
import org.com.itpple.spot.server.model.Role;
import org.com.itpple.spot.server.repository.UserRepository;
import org.com.itpple.spot.server.service.AuthService;
import org.com.itpple.spot.server.service.OAuthServiceFactory;
import org.com.itpple.spot.server.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final OAuthServiceFactory oAuthServiceFactory;
  private final UserRepository userRepository;
  private final TokenProvider tokenProvider;
  private final UserService userService;

  @Override
  @Transactional
  public TokenResponse loginWithOAuth(OAuthType oAuthType, String accessToken,
      String refreshToken) {
    var oAuthService = oAuthServiceFactory.getOAuthService(oAuthType);

    var oAuthId = oAuthService.getOAuthIdByToken(accessToken, refreshToken);

    var user = userRepository.findByoAuthId(oAuthId);

    if (user.isEmpty()) {
      var userInfo = oAuthService.getUserInfoByToken(accessToken, refreshToken);

      var newUser = User.builder()
          .oAuthId(oAuthId)
          .role(Role.USER)
          .nickname(userInfo.getNickname())
          .profileImageUrl(userInfo.getProfileImage())
          .build();

      user = Optional.of(userRepository.save(newUser));
    }

    var authentication = this.generateAuthentication(user.get());

    return tokenProvider.generateToken(user.get().getId(),
        authentication);//TODO: authentication를 설정해야 함

  }

  private Authentication generateAuthentication(User user) {
    return tokenProvider.generateAuthentication(user);
  }

  @Override
  public TokenResponse refresh(String refreshToken) {
    if (!this.tokenProvider.validateRefreshToken(refreshToken)) {
      throw new RuntimeException("Refresh Token is not valid");
    }

    var userId = this.tokenProvider.getPayloadFromRefreshToken(refreshToken)
        .get("userId", Long.class);

    return tokenProvider.generateToken(userId, null);//TODO: authentication를 설정해야 함
  }

  @Override
  public void logout(String accessToken) {
  }


}
