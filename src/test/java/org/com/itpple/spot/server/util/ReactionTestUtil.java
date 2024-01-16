package org.com.itpple.spot.server.util;

import org.com.itpple.spot.server.domain.pot.entity.Pot;
import org.com.itpple.spot.server.domain.reaction.entity.Reaction;
import org.com.itpple.spot.server.domain.user.entity.User;
import org.com.itpple.spot.server.global.common.constant.ReactionType;

public class ReactionTestUtil {

	public static Reaction create(ReactionType reactionType) {
		return Reaction.builder()
			.pot(PotTestUtil.create())
			.user(UserTestUtil.create())
			.reactionType(reactionType)
			.build();
	}

	public static Reaction create(Pot pot, User user, ReactionType reactionType, boolean isDeleted) {
		return Reaction.builder()
			.pot(pot)
			.user(user)
			.reactionType(reactionType)
			.isDeleted(isDeleted)
			.build();
	}
}
