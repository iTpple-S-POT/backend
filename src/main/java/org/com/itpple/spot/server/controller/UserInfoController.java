package org.com.itpple.spot.server.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.com.itpple.spot.server.common.auth.Auth;
import org.com.itpple.spot.server.common.auth.CustomUserDetails;
import org.com.itpple.spot.server.exception.MemberIdAlreadyExistsException;
import org.com.itpple.spot.server.model.dto.userInfo.UserRequestDto;
import org.com.itpple.spot.server.service.userInfo.UserInfoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserInfoController {
    private final UserInfoService userInfoService;

    //유저 기본 정보 기입
    @PostMapping("")
    public ResponseEntity<UserRequestDto> basicInfo(
            @Auth CustomUserDetails customUserDetails,
            @RequestBody @Valid UserRequestDto requestDto) {
        try {
            Long userId = customUserDetails.getUserId();
            userInfoService.fillUserInfo(userId, requestDto);
            return ResponseEntity.ok().body(requestDto);
        } catch (RuntimeException e) {
            throw new MemberIdAlreadyExistsException();
        }
    }

    //닉네임 체크
    @GetMapping("/nickname/check")
    public ResponseEntity<Void> nicknameCheck(@RequestParam("nickname") String nickname) {
        userInfoService.validateNickname(nickname);
        userInfoService.isAlreadyExistNickname(nickname);
        return ResponseEntity.ok().build();
    }

}
