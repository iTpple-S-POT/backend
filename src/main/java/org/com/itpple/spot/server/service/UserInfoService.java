package org.com.itpple.spot.server.service;

import org.com.itpple.spot.server.dto.userInfo.request.UserInfoRequest;
import org.com.itpple.spot.server.dto.userInfo.response.UserInfoResponse;

public interface UserInfoService {
    UserInfoResponse fillUserInfo(Long userId, UserInfoRequest userInfoRequest);
    void isAlreadyExistNickname(String nickname);
    void validateNickname(String nickname);
    UserInfoResponse getUserInfo(Long userId);
    UserInfoResponse updateUserInfo(Long userId, UserInfoRequest userInfoRequest);
}
