package org.com.itpple.spot.server.domain.signup.controller;

import lombok.RequiredArgsConstructor;
import org.com.itpple.spot.server.common.Dto.NormalResponseDto;
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

@RestController
@RequestMapping("/app")
@RequiredArgsConstructor
public class SignupController {
    private final SingupService singupService;

    @PostMapping("/signup")
    public ResponseEntity<NormalResponseDto> join(@RequestBody @Valid MemberRequestDto requestDto) {
        singupService.joinMember(requestDto);
        return ResponseEntity.ok(NormalResponseDto.success());
    }
    //닉네임 중복 체크
    @PostMapping("/signup/nickname-check")
    public ResponseEntity<NormalResponseDto> checkDuplicateNickname(@RequestBody @Valid NicknameRequestDto requestDto) {
        if (isAlreadyExistNickname(requestDto.getNickname()))
            return ResponseEntity.ok(NormalResponseDto.fail());
        return ResponseEntity.ok(NormalResponseDto.success());
    }
    private boolean isAlreadyExistNickname(String nickname) {
        return singupService.findMemberByNickname(nickname);
    }

}
