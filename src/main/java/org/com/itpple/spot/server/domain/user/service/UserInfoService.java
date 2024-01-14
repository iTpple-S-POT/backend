package org.com.itpple.spot.server.domain.user.service;

import org.com.itpple.spot.server.domain.user.dto.request.UserInfoRequest;
import org.com.itpple.spot.server.domain.user.dto.response.UserInfoResponse;

public interface UserInfoService {
    UserInfoResponse fillUserInfo(Long userId, UserInfoRequest userInfoRequest);
    void isAlreadyExistNickname(String nickname);
    void validateNickname(String nickname);
    UserInfoResponse getUserInfo(Long userId);
    UserInfoResponse updateUserInfo(Long userId, UserInfoRequest userInfoRequest);
}
