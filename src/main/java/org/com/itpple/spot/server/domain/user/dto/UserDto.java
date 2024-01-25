package org.com.itpple.spot.server.domain.user.dto;

import org.com.itpple.spot.server.domain.user.entity.User;
import org.com.itpple.spot.server.global.common.constant.*;

import java.time.LocalDate;
import java.util.List;

public record UserDto(
		Long id,
		String loginType,
		String socialId,
		Role role,
		String name,
		String profileImageUrl,
		String phoneNumber,
		String nickname,
		LocalDate birthDay,
		Gender gender,
		Mbti mbti,
		List<Interest> interests,
		Status status
) {

	public static UserDto of(
			Long id, String loginType, String socialId, Role role, String name, String profileImageUrl, String phoneNumber,
			String nickname, LocalDate birthDay, Gender gender, Mbti mbti, List<Interest> interests, Status status
	) {
		return new UserDto(
				id, loginType, socialId, role, name, profileImageUrl,
				phoneNumber, nickname, birthDay, gender, mbti, interests, status
		);
	}

	public static UserDto from(User user) {
		return of(
				user.getId(),
				user.getLoginType(),
				user.getSocialId(),
				user.getRole(),
				user.getName(),
				user.getProfileImageUrl(),
				user.getPhoneNumber(),
				user.getNickname(),
				user.getBirthDay(),
				user.getGender(),
				user.getMbti(),
				user.getInterests(),
				user.getStatus()
		);
	}

	public User toEntity() {
		return User.builder()
				.loginType(this.loginType)
				.socialId(this.socialId)
				.role(this.role)
				.name(this.name)
				.profileImageUrl(this.profileImageUrl)
				.phoneNumber(this.phoneNumber)
				.nickname(this.nickname)
				.birthDay(this.birthDay)
				.gender(this.gender)
				.mbti(this.mbti)
				.interests(this.interests)
				.status(this.status)
				.build();
	}
}
