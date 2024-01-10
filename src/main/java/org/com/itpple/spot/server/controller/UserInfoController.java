package org.com.itpple.spot.server.controller;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.com.itpple.spot.server.common.auth.Auth;
import org.com.itpple.spot.server.common.auth.CustomUserDetails;
import org.com.itpple.spot.server.dto.userInfo.request.UserInfoRequest;
import org.com.itpple.spot.server.dto.userInfo.response.UserInfoResponse;
import org.com.itpple.spot.server.service.impl.UserInfoServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserInfoController {
    private final UserInfoServiceImpl userInfoService;

    //유저 기본 정보 기입
    @PostMapping("")
    public ResponseEntity<UserInfoResponse> basicInfo(
            @Auth CustomUserDetails customUserDetails,
            @RequestBody @Valid UserInfoRequest userInfoRequest) {
        Long userId = customUserDetails.getUserId();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userInfoService.fillUserInfo(userId, userInfoRequest));
    }

    //닉네임 체크
    @GetMapping("/nickname/check")
    public ResponseEntity<Void> nicknameCheck(@RequestParam("nickname") String nickname) {
        userInfoService.validateNickname(nickname);
        userInfoService.isAlreadyExistNickname(nickname);
        return ResponseEntity.ok().build();
    }

    //유저 조회
    @GetMapping("/info")
    public ResponseEntity<UserInfoResponse> getUserInfo(@Auth CustomUserDetails customUserDetails) {
        Long userId = customUserDetails.getUserId();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userInfoService.getUserInfo(userId));
    }

    //유저 정보 수정
    @PutMapping("/update")
    public ResponseEntity<UserInfoResponse> updateUserInfo(
            @Auth CustomUserDetails customUserDetails,
            @Valid @RequestBody UserInfoRequest userInfoRequest
    ) {
        Long userId = customUserDetails.getUserId();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userInfoService.updateUserInfo(userId, userInfoRequest));
    }


}
