package org.com.itpple.spot.server.repository;

import java.util.Optional;
import org.com.itpple.spot.server.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByNickname(String nickname);
    Optional<User> findByMemberId(long memberId);
    Optional<User> findBySocialId(String socialId);
}
