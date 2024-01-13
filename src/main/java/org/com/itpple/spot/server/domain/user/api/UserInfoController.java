package org.com.itpple.spot.server.domain.user.api;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.com.itpple.spot.server.domain.user.dto.request.UserInfoRequest;
import org.com.itpple.spot.server.domain.user.dto.response.UserInfoResponse;
import org.com.itpple.spot.server.domain.user.service.UserInfoService;
import org.com.itpple.spot.server.global.auth.Auth;
import org.com.itpple.spot.server.global.auth.userDetails.CustomUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserInfoController {
    private final UserInfoService userInfoService;

    //유저 기본 정보 기입
    @PostMapping("")
    public ResponseEntity<UserInfoResponse> basicInfo(
            @Auth CustomUserDetails customUserDetails,
            @RequestBody @Valid UserInfoRequest userInfoRequest) {
        Long userId = customUserDetails.getUserId();
        return ResponseEntity.ok(userInfoService.fillUserInfo(userId, userInfoRequest));
    }

    //닉네임 체크
    @GetMapping("/nickname/check")
    public ResponseEntity<Void> nicknameCheck(@RequestParam("nickname") String nickname) {
        userInfoService.validateNickname(nickname);
        userInfoService.isAlreadyExistNickname(nickname);
        return ResponseEntity.ok().build();
    }

    //유저 조회
    @GetMapping("")
    public ResponseEntity<UserInfoResponse> getUserInfo(@Auth CustomUserDetails customUserDetails) {
        Long userId = customUserDetails.getUserId();
        return ResponseEntity.ok(userInfoService.getUserInfo(userId));
    }

    //유저 정보 수정
    @PutMapping("")
    public ResponseEntity<UserInfoResponse> updateUserInfo(
            @Auth CustomUserDetails customUserDetails,
            @Valid @RequestBody UserInfoRequest userInfoRequest
    ) {
        Long userId = customUserDetails.getUserId();
        return ResponseEntity.ok(userInfoService.updateUserInfo(userId, userInfoRequest));
    }
}
