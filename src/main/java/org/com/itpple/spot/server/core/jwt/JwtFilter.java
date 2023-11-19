package org.com.itpple.spot.server.core.jwt;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

  public static final String AUTHORIZATION_HEADER = "Authorization";

  public final TokenProvider tokenProvider;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain) throws ServletException, IOException {
    var accessToken = resolveAccessToken(request);
    var requestURI = (request).getRequestURI();

    if (StringUtils.hasText(accessToken) && tokenProvider.validateAccessToken(accessToken)) {
      var authentication = tokenProvider.getAuthentication(accessToken);
      request.setAttribute("authentication", authentication);
      SecurityContextHolder.getContext().setAuthentication(authentication);
      log.info("Security Context에 '{}' 인증 정보를 저장했습니다, uri: {}", authentication.getName(),
          requestURI);
    } else {
      log.info("유효한 JWT 토큰이 없습니다, uri: {}", requestURI);
    }

    chain.doFilter(request, response);
  }

  private String resolveAccessToken(HttpServletRequest request) {
    var bearerToken = request.getHeader(AUTHORIZATION_HEADER);
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }
    return null;
  }
}
