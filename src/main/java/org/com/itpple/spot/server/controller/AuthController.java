package org.com.itpple.spot.server.controller;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.com.itpple.spot.server.dto.oAuth.RefreshTokenRequest;
import org.com.itpple.spot.server.dto.oAuth.TokenResponse;
import org.com.itpple.spot.server.model.OAuthType;
import org.com.itpple.spot.server.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @GetMapping("/test")
  public ResponseEntity<?> test() {
    return ResponseEntity.ok("test");
  }

  @PostMapping("/login/{providerType}")
  public ResponseEntity<TokenResponse> login(@PathVariable("providerType") OAuthType providerType,
      @RequestParam("accessToken") String accessToken,
      @RequestParam("refreshToken") String refreshToken) {

    var tokenResponse = authService.loginWithOAuth(providerType, accessToken, refreshToken);

    return ResponseEntity.ok(tokenResponse);
  }

  @PostMapping("/refresh")
  public ResponseEntity<TokenResponse> refresh(
      @Valid @RequestBody() RefreshTokenRequest refreshTokenRequest) {
    var tokenResponse = authService.refresh(refreshTokenRequest.getRefreshToken());
    return ResponseEntity.ok(tokenResponse);
  }
}
