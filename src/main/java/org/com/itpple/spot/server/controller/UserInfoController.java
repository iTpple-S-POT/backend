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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

}
