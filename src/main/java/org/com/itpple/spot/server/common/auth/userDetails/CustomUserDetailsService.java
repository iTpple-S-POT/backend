package org.com.itpple.spot.server.common.auth.userDetails;

import lombok.RequiredArgsConstructor;
import org.com.itpple.spot.server.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetails loadUserByUserId(Long userId) throws UsernameNotFoundException {
        return userRepository.findById(userId)
                .map(CustomUserDetails::from)
                .orElseThrow(() -> new UsernameNotFoundException("해당 유저를 찾을 수 없습니다."));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findById(Long.valueOf(username))
                .map(CustomUserDetails::from)
                .orElseThrow(() -> new UsernameNotFoundException("해당 유저를 찾을 수 없습니다."));
    }

}
