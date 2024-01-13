package org.com.itpple.spot.server.global.auth.repository;

import java.util.Optional;

public interface RefreshTokenRepository {

    void save(Long userId, String refreshToken);

    Optional<String> findRefreshTokenByUserId(Long userId);

    void removeByUserId(Long userId);
}
