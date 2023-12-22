package org.com.itpple.spot.server.service.impl;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.com.itpple.spot.server.entity.User;
import org.com.itpple.spot.server.repository.UserRepository;
import org.com.itpple.spot.server.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public Optional<User> findBySocialId(String socialId) {
        return userRepository.findBySocialId(socialId);
    }

    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
}
