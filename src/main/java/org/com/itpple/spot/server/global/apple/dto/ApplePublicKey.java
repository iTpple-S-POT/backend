package org.com.itpple.spot.server.global.apple.dto;

import lombok.Getter;
import org.com.itpple.spot.server.global.apple.exception.AppleLoginException;

import java.util.List;

@Getter
public class ApplePublicKey {

	private List<Key> keys;

	@Getter
	public static class Key {
		private String kty;
		private String kid;
		private String use;
		private String alg;
		private String n;
		private String e;
	}

	public Key getMatchedKey(String kid, String alg) {
		return this.getKeys().stream()
				.filter(key -> key.getKid().equals(kid) && key.getAlg().equals(alg))
				.findFirst()
				.orElseThrow(AppleLoginException::new);
	}
}
