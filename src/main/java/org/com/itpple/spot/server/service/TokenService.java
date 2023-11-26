package org.com.itpple.spot.server.service;

public interface TokenService {

    boolean isRefreshTokenExist(Long userId, String refreshToken);

    void removeRefreshToken(Long userId);

    void saveRefreshToken(Long userId, String newRefreshToken);
}
