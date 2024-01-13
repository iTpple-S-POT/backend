package org.com.itpple.spot.server.domain.user.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.com.itpple.spot.server.global.common.constant.NickNameData;
import org.com.itpple.spot.server.global.common.constant.Status;
import org.com.itpple.spot.server.domain.user.dto.request.UserInfoRequest;
import org.com.itpple.spot.server.domain.user.dto.response.UserInfoResponse;
import org.com.itpple.spot.server.domain.user.entity.User;
import org.com.itpple.spot.server.domain.user.exception.NicknameDuplicateException;
import org.com.itpple.spot.server.domain.user.exception.NicknameValidationException;
import org.com.itpple.spot.server.domain.user.exception.UserIdNotFoundException;
import org.com.itpple.spot.server.domain.user.repository.UserRepository;
import org.com.itpple.spot.server.domain.user.service.UserInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserInfoServiceImpl implements UserInfoService {
    private final UserRepository userRepository;

    @Transactional
    public UserInfoResponse fillUserInfo(Long userId, UserInfoRequest userInfoRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserIdNotFoundException("PK = " + userId));
        if (userInfoRequest.nickname() == null || userInfoRequest.nickname().isEmpty()) {
            String defaultNickname = generateDefaultNickname();
            user.setNickname(defaultNickname);
        } else {
            user.setNickname(userInfoRequest.nickname());
        }
        user.setPhoneNumber(userInfoRequest.phoneNumber());
        user.setBirthDay(userInfoRequest.birthDay());
        user.setGender(userInfoRequest.gender());
        user.setMbti(userInfoRequest.mbti());
        user.setInterests(userInfoRequest.interests());

        if (user.getBirthDay() != null) {
            user.setStatus(Status.COMPLETED);
        } else {
            user.setStatus(Status.PROGRESS);
        }
        User savedUser = userRepository.save(user);
        return UserInfoResponse.from(savedUser);
    }

    private String generateDefaultNickname() {
        Random random = new Random();
        int randomNumbers = random.nextInt(100000);
        return NickNameData.determiners[
                (int) Math.floor(Math.random() * NickNameData.determiners.length)
                ] +
                NickNameData.animals[
                        (int) Math.floor(Math.random() * NickNameData.animals.length)
                        ] +
                randomNumbers;
    }

    public void isAlreadyExistNickname(String nickname) {
        if (userRepository.existsByNickname(nickname)) {
            throw new NicknameDuplicateException();
        }
    }

    public void validateNickname(String nickname) {
        if (nickname == null || nickname.trim().isEmpty()) {
            throw new NicknameValidationException();
        }
        if (!nickname.matches("^[a-zA-Z0-9가-힣_]{1,15}$")) {
            throw new NicknameValidationException();
        }
    }

    public UserInfoResponse getUserInfo(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserIdNotFoundException("PK = " + userId));
        return UserInfoResponse.from(user);
    }

    @Transactional
    public UserInfoResponse updateUserInfo(Long userId, UserInfoRequest userInfoRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserIdNotFoundException("PK = " + userId));
        if (userInfoRequest.nickname() != null && !userInfoRequest.nickname().isEmpty()) {
            user.setNickname(userInfoRequest.nickname());
        }
        if (userInfoRequest.phoneNumber() != null && !userInfoRequest.phoneNumber().isEmpty()) {
            user.setPhoneNumber(userInfoRequest.phoneNumber());
        }
        if (userInfoRequest.birthDay() != null) {
            user.setBirthDay(userInfoRequest.birthDay());
        }
        if (userInfoRequest.gender() != null) {
            user.setGender(userInfoRequest.gender());
        }
        if (userInfoRequest.mbti() != null) {
            user.setMbti(userInfoRequest.mbti());
        }
        if (userInfoRequest.interests() != null && !userInfoRequest.interests().isEmpty()) {
            user.setInterests(userInfoRequest.interests());
        }
        User updateUser = userRepository.save(user);
        return UserInfoResponse.from(updateUser);
    }
}