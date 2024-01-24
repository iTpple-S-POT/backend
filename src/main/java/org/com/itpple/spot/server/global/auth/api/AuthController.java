package org.com.itpple.spot.server.global.auth.api;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.com.itpple.spot.server.domain.user.dto.UserDto;
import org.com.itpple.spot.server.global.apple.dto.request.AppleLoginRequest;
import org.com.itpple.spot.server.global.apple.dto.AppleUserInfo;
import org.com.itpple.spot.server.global.apple.service.AppleOAuthService;
import org.com.itpple.spot.server.global.auth.jwt.TokenProvider;
import org.com.itpple.spot.server.global.auth.userDetails.CustomUserDetails;
import org.com.itpple.spot.server.global.common.constant.OAuthType;
import org.com.itpple.spot.server.global.auth.dto.LoginRequest;
import org.com.itpple.spot.server.global.auth.dto.RefreshTokenRequest;
import org.com.itpple.spot.server.global.auth.dto.TokenResponse;
import org.com.itpple.spot.server.global.auth.service.AuthService;
import org.springframework.http.HttpStatus;
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
    private final AppleOAuthService appleOAuthService;
    private final TokenProvider tokenProvider;

    @PostMapping("/login/{providerType}")
    public ResponseEntity<TokenResponse> login(@PathVariable("providerType") OAuthType providerType,
            @RequestBody() LoginRequest loginRequest) {

        var tokenResponse = authService.loginWithOAuth(providerType, loginRequest.getAccessToken(),
                loginRequest.getRefreshToken());

        return ResponseEntity.ok(tokenResponse);
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refresh(
            @Valid @RequestBody() RefreshTokenRequest refreshTokenRequest) {
        var tokenResponse = authService.refresh(refreshTokenRequest.getRefreshToken());
        return ResponseEntity.ok(tokenResponse);
    }

    @PostMapping(value = "/login/apple")
    public ResponseEntity<TokenResponse> appleLogin(@Valid @RequestBody AppleLoginRequest request) {
        AppleUserInfo userInfo = appleOAuthService.getUserInfo(request.identityToken());

        UserDto userDto = authService.findBySocialId(userInfo.getSub())
                .orElseGet(() -> authService.join(userInfo.toUserDto(request.name())));

        CustomUserDetails userDetails = CustomUserDetails.from(userDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(tokenProvider.generateToken(userDetails));
    }
}
