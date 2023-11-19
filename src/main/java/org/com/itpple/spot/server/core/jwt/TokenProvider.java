package org.com.itpple.spot.server.core.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.SignatureException;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.com.itpple.spot.server.dto.oAuth.TokenResponse;
import org.com.itpple.spot.server.repository.RefreshTokenRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenProvider implements InitializingBean {

  private static final String AUTHORITIES_KEY = "auth";

  @Value("${jwt.accessTokenSecret}")
  private String ACCESS_TOKEN_SECRET_KEY;

  @Value("${jwt.accessTokenExpiredSeconds}")
  private Long ACCESS_TOKEN_EXPIRED_SECONDS;

  @Value("${jwt.refreshTokenSecret}")
  private String REFRESH_TOKEN_SECRET_KEY;

  @Value("${jwt.refreshTokenExpiredSeconds}")
  private Long REFRESH_TOKEN_EXPIRED_SECONDS;

  private SecretKey accessTokenKey;
  private SecretKey refreshTokenKey;


  private final RefreshTokenRepository refreshTokenRepository;

  @Override
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
        .claim(AUTHORITIES_KEY, authentication)
        .claim("userId", userId)
        .issuedAt(new Date())
        .expiration(new Date(new Date().getTime() + ACCESS_TOKEN_EXPIRED_SECONDS))
        .header()
        .type("JWT")
        .and()
        .compact();
  }

  private String generateRefreshToken(Long userId) {
    var refreshToken = Jwts.builder()
        .signWith(refreshTokenKey)
        .claim("userId", userId)
        .issuedAt(new Date())
        .expiration(new Date(new Date().getTime() + REFRESH_TOKEN_EXPIRED_SECONDS))
        .header()
        .type("JWT")
        .and()
        .compact();

    this.refreshTokenRepository.save(userId, refreshToken);

    return refreshToken;
  }

  public boolean validateAccessToken(String accessToken) {
    try {
      Jwts.parser().verifyWith(accessTokenKey).build().parseSignedClaims(accessToken);
      return true;
    } catch (SecurityException | MalformedJwtException e) {
      log.info("잘못된 JWT 서명입니다.");
    } catch (ExpiredJwtException e) {
      log.info("만료된 JWT 토큰입니다.");
    } catch (IllegalArgumentException e) {
      log.info("JWT 토큰이 잘못되었습니다.");
    } catch (UnsupportedJwtException e) {
      log.info("지원되지 않는 JWT 토큰입니다.");
    } catch (SignatureException e) {
      log.info("JWT 서명이 잘못되었습니다.");
    }

    return false;
  }

  public boolean validateRefreshToken(String refreshToken) {
    var claims = Jwts.parser().verifyWith(refreshTokenKey).build()
        .parseSignedClaims(refreshToken);
    var userId = (Long) claims.getPayload().get("userId");

    var storedRefreshToken = refreshTokenRepository.findRefreshTokenByUserId(userId)
        .orElseThrow(IllegalArgumentException::new);

    if (!storedRefreshToken.equals(refreshToken)) {
      refreshTokenRepository.removeByUserId(userId);
      return false;
    }
    return true;
  }

  public Authentication getAuthentication(String accessToken) {
    Claims claims = this.getPayload(accessToken);

    Collection<GrantedAuthority> authorities = new HashSet<>();
    authorities.add((GrantedAuthority) claims.get("auth"));

    User principal = new User(claims.getSubject(), "", authorities);

    return new UsernamePasswordAuthenticationToken(principal, accessToken, authorities);

  }

  public Claims getPayload(String accessToken) {
    return Jwts.parser().verifyWith(accessTokenKey).build()
        .parseSignedClaims(accessToken).getPayload();
  }

  public Claims getPayloadFromRefreshToken(String refreshToken) {
    return Jwts.parser().verifyWith(refreshTokenKey).build()
        .parseSignedClaims(refreshToken).getPayload();
  }
}
