package org.com.itpple.spot.server.domain.user.service;

import org.com.itpple.spot.server.domain.user.dto.UserInfoDto;
import org.com.itpple.spot.server.domain.user.dto.UserProfileDto;
import org.com.itpple.spot.server.domain.user.dto.request.UpdateUserInfoRequest;
import org.com.itpple.spot.server.domain.user.dto.request.UserInfoRequest;
import org.com.itpple.spot.server.domain.user.dto.response.UserInfoResponse;

public interface UserInfoService {
    UserInfoResponse fillUserInfo(Long userId, UserInfoRequest userInfoRequest);
    void isAlreadyExistNickname(String nickname);
    void validateNickname(String nickname);
    UserInfoDto getUserInfo(Long userId);
    UserProfileDto getUserProfile(Long userId);
    UserInfoResponse updateUserInfo(Long userId, UpdateUserInfoRequest updateUserInfoRequest);
    void deleteUserInfo(Long userId);
}
