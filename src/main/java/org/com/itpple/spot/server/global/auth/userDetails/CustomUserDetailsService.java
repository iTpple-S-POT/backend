package org.com.itpple.spot.server.global.auth.userDetails;

import lombok.RequiredArgsConstructor;
import org.com.itpple.spot.server.domain.user.dto.UserDto;
import org.com.itpple.spot.server.domain.user.repository.UserRepository;
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
                .map(UserDto::from)
                .map(CustomUserDetails::from)
                .orElseThrow(() -> new UsernameNotFoundException("해당 유저를 찾을 수 없습니다."));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findById(Long.valueOf(username))
                .map(UserDto::from)
                .map(CustomUserDetails::from)
                .orElseThrow(() -> new UsernameNotFoundException("해당 유저를 찾을 수 없습니다."));
    }

}
