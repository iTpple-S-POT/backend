package org.com.itpple.spot.server.global.auth.service.impl;

import lombok.RequiredArgsConstructor;
import org.com.itpple.spot.server.global.auth.repository.RefreshTokenRepository;
import org.com.itpple.spot.server.global.auth.service.TokenService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    @Transactional
    public boolean isRefreshTokenExist(Long userId, String refreshToken) {

        var storedRefreshToken = refreshTokenRepository.findRefreshTokenByUserId(userId);

        if (storedRefreshToken.isEmpty() || !storedRefreshToken.get().equals(refreshToken)) {
            refreshTokenRepository.removeByUserId(userId);
            return false;
        }
        return true;
    }

    @Override
    @Transactional
    public void removeRefreshToken(Long userId) {
        refreshTokenRepository.removeByUserId(userId);
    }

    @Override
    @Transactional
    public void saveRefreshToken(Long userId, String newRefreshToken) {
        refreshTokenRepository.save(userId, newRefreshToken);
    }
}
