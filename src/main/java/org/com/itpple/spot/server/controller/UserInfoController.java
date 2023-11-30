package org.com.itpple.spot.server.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.com.itpple.spot.server.model.dto.userInfo.UserRequestDto;
import org.com.itpple.spot.server.model.dto.userInfo.NicknameRequestDto;
import org.com.itpple.spot.server.service.userInfo.UserInfoService;
import org.springframework.http.HttpStatus;
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
    @PostMapping("/fill-basic-userinfo/{memberId}")
    public ResponseEntity<Void> fillBasicUserInfo(
            @PathVariable Long memberId,
            @RequestBody @Valid UserRequestDto requestDto) {
        try {
            userInfoService.fillUserInfo(memberId, requestDto);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            log.info(e.getMessage()); //향후 custom 에러로 처리
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    //닉네임 중복 체크
    @GetMapping("/fill-basic-userinfo/check-duplicate-nickname")
    public ResponseEntity<Void> checkDuplicateNickname(@RequestParam("nickname") @Valid String nickname) {
        try {
            if (userInfoService.isAlreadyExistNickname(nickname)) {
                throw new Exception("중복된 닉네임입니다");
            }
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error(e.getMessage());    //향후 custom 에러로 처리
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

}
