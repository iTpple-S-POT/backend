package org.com.itpple.spot.server.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.com.itpple.spot.server.model.dto.userInfo.UserRequestDto;
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
    @PostMapping("/basic-info/{memberId}")
    public ResponseEntity<Void> basicInfo(
            @PathVariable Long memberId,
            @RequestBody @Valid UserRequestDto requestDto) {
        try {
            userInfoService.fillUserInfo(memberId, requestDto);
            log.info("{}",requestDto.getNickname()); //랜덤으로 설정된 닉네임//문제없으면 추후 삭제
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            log.info(e.getMessage()); //향후 custom 에러로 처리
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    //닉네임 중복 체크
    @GetMapping("/nickname/check")
    public ResponseEntity<Void> nicknameCheck(@RequestParam("nickname") String nickname) {
        try {
            if (userInfoService.isAlreadyExistNickname(nickname)) {
                throw new IllegalArgumentException("중복된 닉네임입니다");
            }
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());    //향후 custom 에러로 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
