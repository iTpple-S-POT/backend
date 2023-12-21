package org.com.itpple.spot.server.core;


import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import org.com.itpple.spot.server.common.jwt.TokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

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
        var role = "ROLE_USER";
        //role to authentication
        var authorities = new HashSet<GrantedAuthority>();
        authorities.add((GrantedAuthority) () -> role);

        var authentication = new UsernamePasswordAuthenticationToken(id, null, authorities);

        var generatedToken = tokenProvider.generateToken(id, authentication);
        var payload = tokenProvider.getClaims(generatedToken.getAccessToken());

        assertAll(() -> assertEquals("1", payload.getSubject()),
                () -> assertTrue(payload.getExpiration().getTime() > System.currentTimeMillis()),
                () -> assertTrue(payload.getIssuedAt().getTime() <= System.currentTimeMillis()));
    }

    @Test
    public void TestExceptionAccessTokenIsModified() {
        var id = 1L;
        var role = "ROLE_USER";
        var authorities = new HashSet<GrantedAuthority>();
        authorities.add((GrantedAuthority) () -> role);

        var authentication = new UsernamePasswordAuthenticationToken(id, null, authorities);

        var accessToken = tokenProvider.generateToken(id, authentication).getAccessToken() + "a";

        assertThrows(RuntimeException.class, () -> tokenProvider.getPayload(accessToken));
    }

    @Test
    public void TestExceptionAccessTokenIsExpired() {
        var id = 1L;
        var role = "ROLE_USER";
        var authorities = new HashSet<GrantedAuthority>();
        authorities.add((GrantedAuthority) () -> role);

        var authentication = new UsernamePasswordAuthenticationToken(id, null, authorities);

        var accessToken = tokenProvider.generateToken(id, authentication).getAccessToken();

        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertThrows(RuntimeException.class, () -> tokenProvider.getPayload(accessToken));
    }
}
