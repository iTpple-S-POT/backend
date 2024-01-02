package org.com.itpple.spot.server.repository;

import org.com.itpple.spot.server.entity.Pot;
import org.com.itpple.spot.server.entity.Reaction;
import org.com.itpple.spot.server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReactionRepository extends JpaRepository<Reaction, Long> {

    boolean existsByPotAndUser(Pot pot, User user);
}
