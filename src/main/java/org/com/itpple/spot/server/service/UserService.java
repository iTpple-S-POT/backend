package org.com.itpple.spot.server.service;

import java.util.Optional;
import org.com.itpple.spot.server.entity.User;

public interface UserService {

    User save(User user);

    Optional<User> findByoAuthId(String oAuthId);

    Optional<User> findById(Long id);
}
