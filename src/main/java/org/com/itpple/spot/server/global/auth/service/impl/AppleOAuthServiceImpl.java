package org.com.itpple.spot.server.global.auth.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.com.itpple.spot.server.global.auth.dto.UserInfo;
import org.com.itpple.spot.server.global.auth.dto.apple.dto.ApplePublicKey;
import org.com.itpple.spot.server.global.auth.dto.apple.dto.AppleUserInfo;
import org.com.itpple.spot.server.global.auth.exception.AppleLoginException;
import org.com.itpple.spot.server.global.auth.exception.TokenExpiredException;
import org.com.itpple.spot.server.global.auth.exception.TokenValidException;
import org.com.itpple.spot.server.global.auth.service.OAuthService;
import org.com.itpple.spot.server.global.common.constant.OAuthType;
import org.com.itpple.spot.server.global.external.apple.AppleApiClient;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AppleOAuthServiceImpl implements OAuthService {

	private static final OAuthType OAUTH_TYPE = OAuthType.APPLE;
	private final AppleApiClient appleApiClient;

	@Override
	public OAuthType getOAuthType() {
		return OAUTH_TYPE;
	}

	@Override
	public String getSocialIdByToken(String identityToken) {
		Map<String, String> identityTokenHeader = parseHeader(identityToken);
		PublicKey publicKey = getApplePublicKey(identityTokenHeader);
		Claims claims = parseClaims(identityToken, publicKey);

		return this.generateSocialId(claims.getSubject());
	}

	@Override
	public UserInfo getUserInfoByToken(String accessToken) {
		Map<String, String> identityTokenHeader = parseHeader(accessToken);
		PublicKey publicKey = getApplePublicKey(identityTokenHeader);
		Claims claims = parseClaims(accessToken, publicKey);
		AppleUserInfo appleUserInfo = AppleUserInfo.from(claims);

		var socialId = this.generateSocialId(appleUserInfo.getSub());

		return UserInfo.builder().socialId(socialId).build();
	}

	private String generateSocialId(String sub) {
		return OAUTH_TYPE.getName() + "_" + sub;
	}

	private Map<String, String> parseHeader(String identityToken) {
		try {
			Map<String, String> identityTokenHeader = new ObjectMapper().readValue(
					Base64.getDecoder().decode(identityToken.substring(0, identityToken.indexOf("."))), Map.class);
			return identityTokenHeader;
		} catch (IOException ex) {
			throw new AppleLoginException();
		}
	}

	private PublicKey getApplePublicKey(Map<String, String> identityTokenHeader) {
		ApplePublicKey applePublicKey = appleApiClient.getApplePublicKey();

		ApplePublicKey.Key matchedKey = applePublicKey.getMatchedKey(
				String.valueOf(identityTokenHeader.get("kid")),
				String.valueOf(identityTokenHeader.get("alg"))
		);

		BigInteger n = new BigInteger(1, Base64.getUrlDecoder().decode(matchedKey.getN()));
		BigInteger e = new BigInteger(1, Base64.getUrlDecoder().decode(matchedKey.getE()));

		try {
			KeyFactory keyFactory = KeyFactory.getInstance(matchedKey.getKty());
			return keyFactory.generatePublic(new RSAPublicKeySpec(n, e));
		} catch (InvalidKeySpecException | NoSuchAlgorithmException ex) {
			throw new AppleLoginException();
		}
	}

	private Claims parseClaims(String identityToken, PublicKey publicKey) {
		try {
			return Jwts.parser()
					.setSigningKey(publicKey)
					.build()
					.parseClaimsJws(identityToken)
					.getBody();
		} catch (ExpiredJwtException ex) {
			throw new TokenExpiredException();
		} catch (Exception ex) {
			throw new TokenValidException();
		}
	}
}
