package org.com.itpple.spot.server.domain.reaction.repository;

import org.com.itpple.spot.server.domain.pot.entity.Pot;
import org.com.itpple.spot.server.domain.reaction.entity.Reaction;
import org.com.itpple.spot.server.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ReactionRepository extends JpaRepository<Reaction, Long> {

    boolean existsByPotAndUser(Pot pot, User user);

    @Query("SELECT r FROM Reaction r WHERE r.id = :reactionId AND r.user.id = :userId")
    Optional<Reaction> findByIdAndUserId(@Param("reactionId") Long reactionId, @Param("userId") Long userId);
}
