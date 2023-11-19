package org.com.itpple.spot.server.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OAuthType {
  KAKAO("kakao"),
  APPLE("apple");

  private final String name;

}
