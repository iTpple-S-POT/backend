package org.com.itpple.spot.server.service;

import java.util.Optional;
import org.com.itpple.spot.server.model.entity.User;

public interface UserService {

    User save(User user);

    Optional<User> findBySocialId(String socialId);

    Optional<User> findById(Long id);
}
