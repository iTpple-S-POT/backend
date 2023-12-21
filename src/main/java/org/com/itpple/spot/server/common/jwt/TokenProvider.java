package org.com.itpple.spot.server.common.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.SignatureException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.com.itpple.spot.server.dto.Payload;
import org.com.itpple.spot.server.dto.oAuth.TokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenProvider {

    private static final String AUTHORITIES_KEY = "auth";
    private static final String USER_ID_KEY = "userId";

    private String ACCESS_TOKEN_SECRET_KEY;

    private Long ACCESS_TOKEN_EXPIRED_SECONDS;

    private String REFRESH_TOKEN_SECRET_KEY;


    private Long REFRESH_TOKEN_EXPIRED_SECONDS;

    private SecretKey accessTokenKey;
    private SecretKey refreshTokenKey;

    @Autowired
    public TokenProvider(
            @Value("${jwt.accessTokenSecret}")
            String accessTokenSecretKey,
            @Value("${jwt.accessTokenExpiredSeconds}")
            Long accessTokenExpiredSeconds,
            @Value("${jwt.refreshTokenSecret}")
            String refreshTokenSecretKey,
            @Value("${jwt.refreshTokenExpiredSeconds}")
            Long refreshTokenExpiredSeconds
    ) {
        this.ACCESS_TOKEN_SECRET_KEY = accessTokenSecretKey;
        this.ACCESS_TOKEN_EXPIRED_SECONDS = accessTokenExpiredSeconds;
        this.REFRESH_TOKEN_SECRET_KEY = refreshTokenSecretKey;
        this.REFRESH_TOKEN_EXPIRED_SECONDS = refreshTokenExpiredSeconds;
    }

    @PostConstruct
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(ACCESS_TOKEN_SECRET_KEY);
        this.accessTokenKey = new SecretKeySpec(keyBytes, "HmacSHA256");

        byte[] refreshKeyBytes = Decoders.BASE64.decode(REFRESH_TOKEN_SECRET_KEY);
        this.refreshTokenKey = new SecretKeySpec(refreshKeyBytes, "HmacSHA256");
    }

    public TokenResponse generateToken(Long userId, Authentication authentication) {
        String accessToken = generateAccessToken(userId, authentication);
        String refreshToken = generateRefreshToken(userId);
        return new TokenResponse(accessToken, refreshToken);
    }

    private String generateAccessToken(Long userId, Authentication authentication) {
        return Jwts.builder()
                .signWith(accessTokenKey)
                .subject(userId.toString())
                .claim(AUTHORITIES_KEY,
                        authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                                .collect(
                                        Collectors.joining(",")))
                .claim(USER_ID_KEY, userId)
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + ACCESS_TOKEN_EXPIRED_SECONDS * 1000))
                .header()
                .type("JWT")
                .and()
                .compact();
    }

    private String generateRefreshToken(Long userId) {

        return Jwts.builder()
                .signWith(refreshTokenKey)
                .claim(USER_ID_KEY, userId)
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + REFRESH_TOKEN_EXPIRED_SECONDS * 1000))
                .header()
                .type("JWT")
                .and()
                .compact();
    }

    public boolean validateAccessToken(String accessToken) {
        try {
            Jwts.parser().verifyWith(accessTokenKey).build().parseSignedClaims(accessToken);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");//향후 custom 에러로 처리
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");//향후 custom 에러로 처리
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");//향후 custom 에러로 처리
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");//향후 custom 에러로 처리
        } catch (SignatureException e) {
            log.info("JWT 서명이 잘못되었습니다.");//향후 custom 에러로 처리
        }

        return false;
    }

    public boolean validateRefreshToken(String refreshToken) {
        try {
            Jwts.parser().verifyWith(refreshTokenKey).build().parseSignedClaims(refreshToken);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");//향후 custom 에러로 처리
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");//향후 custom 에러로 처리
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");//향후 custom 에러로 처리
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");//향후 custom 에러로 처리
        } catch (SignatureException e) {
            log.info("JWT 서명이 잘못되었습니다.");//향후 custom 에러로 처리
        }

        return false;
    }

    public Authentication generateAuthentication(
            org.com.itpple.spot.server.entity.User user) {

        var authorities = new HashSet<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));

        var principal = new User(user.getId().toString(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, null, authorities);
    }

    public Authentication getAuthentication(String accessToken) {
        var claims = this.getClaims(accessToken);

        var authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .toList();

        var principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, null, authorities);
    }

    public Payload getPayload(String accessToken) {
        var claims = this.getClaims(accessToken);
        var userId = claims.get(USER_ID_KEY, Long.class);

        return Payload.of(userId);
    }

    public Claims getClaims(String accessToken) {
        return Jwts.parser().verifyWith(accessTokenKey).build()
                .parseSignedClaims(accessToken).getPayload();
    }

    public Long getUserIdFromRefreshToken(String refreshToken) {
        return Jwts.parser().verifyWith(refreshTokenKey).build()
                .parseSignedClaims(refreshToken).getPayload().get(USER_ID_KEY, Long.class);
    }
}
