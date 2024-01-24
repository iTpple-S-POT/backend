package org.com.itpple.spot.server.util;

import static org.com.itpple.spot.server.global.util.GeometryUtil.createPoint;

import java.time.LocalDateTime;
import org.com.itpple.spot.server.domain.location.dto.PointDTO;
import org.com.itpple.spot.server.domain.pot.domain.category.entity.Category;
import org.com.itpple.spot.server.domain.pot.entity.Pot;
import org.com.itpple.spot.server.domain.user.entity.User;
import org.com.itpple.spot.server.global.common.constant.PotType;

public class PotTestUtil {

	public static Pot create() {
		return Pot.builder()
			.user(UserTestUtil.create())
			.category(createCategory())
			.potType(PotType.TEXT)
			.imageKey("imageKey")
			.location(createPoint(new PointDTO(1.0, 1.0)))
			.expiredAt(LocalDateTime.MAX)
			.build();
	}

	public static Pot create(User user, Category category) {
		return Pot.builder()
			.user(user)
			.category(category)
			.potType(PotType.TEXT)
			.imageKey("imageKey")
			.location(createPoint(new PointDTO(1.0, 1.0)))
			.expiredAt(LocalDateTime.MAX)
			.build();
	}

	public static Category createCategory() {
		return Category.builder()
			.name("name")
			.build();
	}
}
