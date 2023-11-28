package org.com.itpple.spot.server.service.impl;

import lombok.RequiredArgsConstructor;
import org.com.itpple.spot.server.repository.RefreshTokenRepository;
import org.com.itpple.spot.server.service.TokenService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public boolean isRefreshTokenExist(Long userId, String refreshToken) {

        var storedRefreshToken = refreshTokenRepository.findRefreshTokenByUserId(userId);

        if (storedRefreshToken.isEmpty() || !storedRefreshToken.get().equals(refreshToken)) {
            refreshTokenRepository.removeByUserId(userId);
            return false;
        }
        return true;
    }

    @Override
    public void removeRefreshToken(Long userId) {
        refreshTokenRepository.removeByUserId(userId);
    }

    @Override
    public void saveRefreshToken(Long userId, String newRefreshToken) {
        refreshTokenRepository.save(userId, newRefreshToken);
    }
}
