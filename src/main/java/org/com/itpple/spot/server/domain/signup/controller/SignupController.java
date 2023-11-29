package org.com.itpple.spot.server.domain.signup.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.com.itpple.spot.server.domain.signup.request.MemberRequestDto;
import org.com.itpple.spot.server.domain.signup.request.NicknameRequestDto;
import org.com.itpple.spot.server.domain.signup.service.SingupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/app")
@RequiredArgsConstructor
public class SignupController {
    private final SingupService singupService;

    //유저 기본 정보 기입
    @PostMapping("/signup")
    public ResponseEntity<Void> join(@RequestBody @Valid MemberRequestDto requestDto) {
        singupService.joinMember(requestDto);
        return ResponseEntity.ok().build();
    }

    //닉네임 중복 체크
    @PostMapping("/signup/nickname-check")
    public ResponseEntity<Void> checkDuplicateNickname(@RequestBody @Valid NicknameRequestDto requestDto) {
        try {
            if (singupService.isAlreadyExistNickname(requestDto.getNickname())) {
                throw new Exception("중복된 닉네임입니다");
            }
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error(e.getMessage());    //향후 custom 에러로 처리
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

}
