package org.com.itpple.spot.server.core.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
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

  @Value("${jwt.accessTokenSecret}")
  private String accessTokenSecretKey;

  @Value("${jwt.accessTokenExpiredSeconds}")
  private Long accessTokenExpiredSeconds;

  @Value("${jwt.refreshTokenSecret}")
  private String refreshTokenSecretKey;

  @Value("${jwt.refreshTokenExpiredSeconds}")
  private Long refreshTokenExpiredSeconds;

  private SecretKey accessTokenKey;
  private SecretKey refreshTokenKey;

  private final RefreshTokenRepository refreshTokenRepository;

  @Override
  public void afterPropertiesSet() {
    byte[] keyBytes = Decoders.BASE64.decode(accessTokenSecretKey);
    this.accessTokenKey = new SecretKeySpec(keyBytes, "HmacSHA256");

    byte[] refreshKeyBytes = Decoders.BASE64.decode(refreshTokenSecretKey);
    this.refreshTokenKey = new SecretKeySpec(refreshKeyBytes, "HmacSHA256");
  }

  public TokenResponse generateToken(Long userId, String role) {
    String accessToken = generateAccessToken(userId, role);
    String refreshToken = generateRefreshToken(userId);
    return new TokenResponse(accessToken, refreshToken);
  }

  private String generateAccessToken(Long userId, String role) {
    return Jwts.builder()
        .signWith(accessTokenKey)
        .claims()
        .add("userId", userId)
        .add("role", role)
        .issuedAt(new Date())
        .expiration(new Date(new Date().getTime() + accessTokenExpiredSeconds))
        .and()
        .header()
        .type("JWT")
        .and()
        .compact();
  }

  private String generateRefreshToken(Long userId) {
    var refreshToken = Jwts.builder()
        .signWith(refreshTokenKey)
        .claims()
        .add("userId", userId)
        .issuedAt(new Date())
        .expiration(new Date(new Date().getTime() + refreshTokenExpiredSeconds))
        .and()
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
    authorities.add((GrantedAuthority) () -> "USER");

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
