package org.com.itpple.spot.server.service.userInfo;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.com.itpple.spot.server.dto.userInfo.UserRequestDto;
import org.com.itpple.spot.server.entity.User;
import org.com.itpple.spot.server.exception.MemberIdAlreadyExistsException;
import org.com.itpple.spot.server.exception.NicknameDuplicateException;
import org.com.itpple.spot.server.exception.NicknameValidationException;
import org.com.itpple.spot.server.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserInfoService {
    private final UserRepository userRepository;

    @Transactional
    public void fillUserInfo(Long memberId, UserRequestDto requestDto) {
        Optional<User> existingMember = userRepository.findById(memberId);

        if (existingMember.isPresent()) {
            throw new MemberIdAlreadyExistsException();
        }
        User newUser = requestDto.toUser();
        userRepository.save(newUser);
    }

    public void isAlreadyExistNickname(String nickname) {
        if(userRepository.existsByNickname(nickname))
            throw new NicknameDuplicateException();
    }

    public void validateNickname(String nickname) {
        if (nickname == null || nickname.trim().isEmpty()) {
            throw new NicknameValidationException();
        }
        if (!nickname.matches("^[a-zA-Z0-9가-힣_]{1,15}$")) {
            throw new NicknameValidationException();
        }
    }

}
