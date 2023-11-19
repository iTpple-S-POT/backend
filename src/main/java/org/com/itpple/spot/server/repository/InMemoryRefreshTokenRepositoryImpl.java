package org.com.itpple.spot.server.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryRefreshTokenRepositoryImpl implements RefreshTokenRepository {

  private final Map<Long, String> refreshTokenMap = new HashMap<>();

  @Override
  public void save(Long userId, String refreshToken) {
    refreshTokenMap.put(userId, refreshToken);
  }

  @Override
  public Optional<String> findRefreshTokenByUserId(Long userId) {
    try {
      return Optional.ofNullable(refreshTokenMap.get(userId));
    } catch (Exception e) {
      return Optional.empty();
    }
  }

  @Override
  public void removeByUserId(Long userId) {

  }
}
