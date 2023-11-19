package org.com.itpple.spot.server.service;

import java.util.Optional;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.com.itpple.spot.server.core.jwt.TokenProvider;
import org.com.itpple.spot.server.dto.oAuth.TokenResponse;
import org.com.itpple.spot.server.entity.User;
import org.com.itpple.spot.server.model.OAuthType;
import org.com.itpple.spot.server.repository.UserRepository;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final OAuthServiceFactory oAuthServiceFactory;
  private final UserRepository userRepository;
  private final TokenProvider tokenProvider;

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
          .username(userInfo.getNickname())
          .password(oAuthId)
          .build();

      user = Optional.of(userRepository.save(newUser));
    }

    return tokenProvider.generateToken(user.get().getId(), null);

  }

  @Override
  public TokenResponse refresh(String refreshToken) {
    if (!this.tokenProvider.validateRefreshToken(refreshToken)) {
      throw new RuntimeException("Refresh Token is not valid");
    }

    var userId = this.tokenProvider.getPayloadFromRefreshToken(refreshToken)
        .get("userId", Long.class);

    return tokenProvider.generateToken(userId, null);
  }

  @Override
  public void logout(String accessToken) {

  }
}
