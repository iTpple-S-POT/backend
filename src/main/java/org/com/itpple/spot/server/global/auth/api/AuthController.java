package org.com.itpple.spot.server.global.auth.api;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.com.itpple.spot.server.global.auth.dto.LoginRequest;
import org.com.itpple.spot.server.global.auth.dto.RefreshTokenRequest;
import org.com.itpple.spot.server.global.auth.dto.TokenResponse;
import org.com.itpple.spot.server.global.auth.service.AuthService;
import org.com.itpple.spot.server.global.common.constant.OAuthType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login/{providerType}")
    public ResponseEntity<TokenResponse> login(@PathVariable("providerType") OAuthType providerType,
            @RequestBody() LoginRequest loginRequest) {

        var tokenResponse = authService.loginWithOAuth(providerType, loginRequest.getAccessToken());

        return ResponseEntity.ok(tokenResponse);
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refresh(
            @Valid @RequestBody() RefreshTokenRequest refreshTokenRequest) {
        var tokenResponse = authService.refresh(refreshTokenRequest.getRefreshToken());
        return ResponseEntity.ok(tokenResponse);
    }
}
