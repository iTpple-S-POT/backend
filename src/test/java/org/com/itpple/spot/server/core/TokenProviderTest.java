package org.com.itpple.spot.server.core;


import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.com.itpple.spot.server.core.jwt.TokenProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TokenProviderTest {

  @InjectMocks
  private TokenProvider generateTokenService;

  @Test
  public void testIssueAccessToken() {
    var id = 1L;
    var role = "ROLE_USER";

    var generatedToken = generateTokenService.generateToken(id, role);
    var payload = generateTokenService.getPayload(generatedToken.getAccessToken());

    assertAll(() -> assertEquals(payload.getId(), "testId"),
        () -> assertTrue(payload.getExpiration().getTime() > System.currentTimeMillis()),
        () -> assertTrue(payload.getIssuedAt().getTime() <= System.currentTimeMillis())
    );
  }

  @Test
  public void testIssueRefreshToken() {
    var id = 1L;

    var generatedToken = generateTokenService.generateToken(id, null);
    var payload = generateTokenService.getPayload(generatedToken.getRefreshToken());

    assertAll(() -> assertEquals(payload.getId(), "testId"),
        () -> assertTrue(payload.getExpiration().getTime() > System.currentTimeMillis()),
        () -> assertTrue(payload.getIssuedAt().getTime() <= System.currentTimeMillis())
    );
  }

  @Test
  public void TestExceptionAccessTokenIsModified() {
    var id = 1L;
    var role = "ROLE_USER";

    var accessToken = generateTokenService.generateToken(id, role).getAccessToken() + "a";

    assertThrows(RuntimeException.class, () -> generateTokenService.getPayload(accessToken));
  }

  @Test
  public void TestExceptionAccessTokenIsExpired() {
    var id = 1L;
    var role = "ROLE_USER";

    var accessToken = generateTokenService.generateToken(id, role).getAccessToken();

    try {
      Thread.sleep(1000L);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    assertThrows(RuntimeException.class, () -> generateTokenService.getPayload(accessToken));
  }
}
