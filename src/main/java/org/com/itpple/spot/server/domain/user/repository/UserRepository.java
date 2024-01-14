package org.com.itpple.spot.server.domain.user.repository;

import java.util.Optional;
import org.com.itpple.spot.server.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByNickname(String nickname);
    Optional<User> findById(Long id);
    Optional<User> findBySocialId(String socialId);
}
