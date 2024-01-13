package org.com.itpple.spot.server.domain.reaction.repository;

import org.com.itpple.spot.server.domain.pot.entity.Pot;
import org.com.itpple.spot.server.domain.reaction.entity.Reaction;
import org.com.itpple.spot.server.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReactionRepository extends JpaRepository<Reaction, Long> {

    boolean existsByPotAndUser(Pot pot, User user);
}
