package org.com.itpple.spot.server.global.config;


import lombok.RequiredArgsConstructor;
import org.com.itpple.spot.server.global.auth.jwt.JwtAccessDeniedHandler;
import org.com.itpple.spot.server.global.auth.jwt.JwtAuthenticationEntryPoint;
import org.com.itpple.spot.server.global.auth.jwt.JwtFilter;
import org.com.itpple.spot.server.global.auth.jwt.TokenProvider;
import org.com.itpple.spot.server.global.auth.userDetails.CustomUserDetailsService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final TokenProvider tokenProvider;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .httpBasic().disable()
                .cors().disable()//TODO: CORS 설정
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/api/v1/auth/login/**").permitAll()
                .antMatchers("/api/v1/auth/refresh").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().disable()
                .addFilterBefore(new JwtFilter(tokenProvider, customUserDetailsService),
                        UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)
                .and()
                .build();
    }
}
