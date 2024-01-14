package org.com.itpple.spot.server.global.auth.jwt;


import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.com.itpple.spot.server.domain.user.entity.User;
import org.com.itpple.spot.server.global.auth.userDetails.CustomUserDetails;
import org.com.itpple.spot.server.global.common.constant.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith({MockitoExtension.class})
public class TokenProviderTest {

    @InjectMocks
    private TokenProvider tokenProvider;

    @BeforeEach
    public void setUp() {
        tokenProvider = new TokenProvider(
                "testKeytestKeytestKeytestKeytestKeytestKeytestKeytestKeytestKeytestKeytestKeytestKeytestKeytestKeytestKeytestKeytestKeytestKeytestKeytestKeytestKey",
                1L,
                "testKeytestKeytestKeytestKeytestKeytestKeytestKeytestKeytestKeytestKeytestKeytestKeytestKeytestKeytestKeytestKeytestKeytestKeytestKeytestKeytestKey",
                1L);
        tokenProvider.afterPropertiesSet();
    }

    @Test
    public void testIssueAccessToken() {
        var id = 1L;
        var role = Role.USER;

        User user = User.builder()
                .id(id)
                .role(role)
                .build();

        var generatedToken = tokenProvider.generateToken(CustomUserDetails.from(user));
        var payload = tokenProvider.getClaims(generatedToken.getAccessToken());

        assertAll(() -> assertEquals("1", payload.getSubject()),
                () -> assertTrue(payload.getExpiration().getTime() > System.currentTimeMillis()),
                () -> assertTrue(payload.getIssuedAt().getTime() <= System.currentTimeMillis()));
    }

    @Test
    public void TestExceptionAccessTokenIsModified() {
        var id = 1L;
        var role = Role.USER;

        User user = User.builder()
                .id(id)
                .role(role)
                .build();

        var accessToken =
                tokenProvider.generateToken(CustomUserDetails.from(user)).getAccessToken() + "a";

        assertThrows(RuntimeException.class, () -> tokenProvider.getClaims(accessToken));
    }

    @Test
    public void TestExceptionAccessTokenIsExpired() {
        var id = 1L;
        var role = Role.USER;

        User user = User.builder()
                .id(id)
                .role(role)
                .build();

        var accessToken = tokenProvider.generateToken(CustomUserDetails.from(user))
                .getAccessToken();

        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertThrows(RuntimeException.class, () -> tokenProvider.getClaims(accessToken));
    }
}
