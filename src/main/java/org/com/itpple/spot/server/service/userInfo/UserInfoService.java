package org.com.itpple.spot.server.service.userInfo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.com.itpple.spot.server.model.dto.userInfo.UserRequestDto;
import org.com.itpple.spot.server.model.entity.User;
import org.com.itpple.spot.server.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserInfoService {
    private final UserRepository userRepository;

    @Transactional
    public void fillUserInfo(Long memberId, UserRequestDto requestDto) {
        Optional<User> existingMember = userRepository.findByMemberId(memberId);

        if (existingMember.isPresent()) {
            throw new RuntimeException("이미 존재하는 memberId입니다");
        }

        User newUser = requestDto.toUser();
        newUser.setMemberId(memberId);
        userRepository.save(newUser);
    }

    public boolean isAlreadyExistNickname(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

}
