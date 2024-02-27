package org.com.itpple.spot.server.domain.reaction.repository;

import org.com.itpple.spot.server.domain.pot.entity.Pot;
import org.com.itpple.spot.server.domain.reaction.dto.ReactionTypeCount;
import org.com.itpple.spot.server.domain.reaction.entity.Reaction;
import org.com.itpple.spot.server.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReactionRepository extends JpaRepository<Reaction, Long> {

    boolean existsByPotAndUser(Pot pot, User user);

    List<Reaction> findByUserId(Long userId);
  
    @Query("SELECT r.reactionType AS reactionType, COUNT(*) AS count " +
            "FROM Reaction r " +
            "WHERE r.pot.id = :potId " +
            "GROUP BY r.reactionType")
    List<ReactionTypeCount> countEachReactionType(@Param("potId") Long potId);
}