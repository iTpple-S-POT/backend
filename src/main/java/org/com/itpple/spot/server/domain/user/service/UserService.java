package org.com.itpple.spot.server.domain.user.service;

import java.util.Optional;
import org.com.itpple.spot.server.domain.user.entity.User;

public interface UserService {

    User save(User user);

    Optional<User> findBySocialId(String socialId);

    Optional<User> findById(Long id);
}
