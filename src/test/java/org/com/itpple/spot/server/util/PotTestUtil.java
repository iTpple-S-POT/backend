package org.com.itpple.spot.server.util;

import java.time.LocalDateTime;

import org.com.itpple.spot.server.domain.pot.category.entity.Category;
import org.com.itpple.spot.server.domain.pot.entity.Pot;
import org.com.itpple.spot.server.domain.user.entity.User;
import org.com.itpple.spot.server.global.common.constant.PotType;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.PrecisionModel;

public class PotTestUtil {

	private static final GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

	public static Pot createPot() {
		return Pot.builder()
			.user(UserTestUtil.createUser())
			.category(createCategory())
			.potType(PotType.TEXT)
			.imageKey("imageKey")
			.location(geometryFactory.createPoint())
			.expiredAt(LocalDateTime.MAX)
			.build();
	}

	public static Pot createPot(User user, Category category) {
		return Pot.builder()
			.user(user)
			.category(category)
			.potType(PotType.TEXT)
			.imageKey("imageKey")
			.location(geometryFactory.createPoint())
			.expiredAt(LocalDateTime.MAX)
			.build();
	}

	public static Category createCategory() {
		return Category.builder()
			.name("name")
			.build();
	}
}
