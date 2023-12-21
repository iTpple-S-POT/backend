package org.com.itpple.spot.server.common.jwt;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.com.itpple.spot.server.repository.UserRepository;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsCustomService implements UserDetailsService, ApplicationContextAware {

    private final UserRepository userRepository;

    private ApplicationContext context;

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext)
            throws BeansException {
        this.context = applicationContext;
    }

    public UserDetails loadUserByUserId(Long userId) throws UsernameNotFoundException {
        return UserDetailsCustom.from(
                userRepository.findById(userId)
                        .orElseThrow(() -> new UsernameNotFoundException("해당 유저를 찾을 수 없습니다.")));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var self = context.getBean(this.getClass());
        return self.loadUserByUserId(Long.parseLong(username));
    }

}
