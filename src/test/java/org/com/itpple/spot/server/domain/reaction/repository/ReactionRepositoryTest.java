package org.com.itpple.spot.server.domain.reaction.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import static org.springframework.test.context.TestConstructor.AutowireMode;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.com.itpple.spot.server.domain.pot.domain.category.entity.Category;
import org.com.itpple.spot.server.domain.pot.entity.Pot;
import org.com.itpple.spot.server.domain.reaction.entity.Reaction;
import org.com.itpple.spot.server.domain.user.entity.User;
import org.com.itpple.spot.server.global.common.constant.ReactionType;
import org.com.itpple.spot.server.util.PotTestUtil;
import org.com.itpple.spot.server.util.ReactionTestUtil;
import org.com.itpple.spot.server.util.UserTestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;

@RequiredArgsConstructor
@TestConstructor(autowireMode = AutowireMode.ALL)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
@DataJpaTest
class ReactionRepositoryTest {

	private final ReactionRepository reactionRepository;
	private final TestEntityManager testEntityManager;

	private final User user = UserTestUtil.create();
	private final Category category = PotTestUtil.createCategory();
	private final Pot pot = PotTestUtil.create(user, category);
	private final ReactionType REACTION_TYPE = ReactionType.GOOD;

	@BeforeEach
	public void setUp() {
		testEntityManager.persist(user);
		testEntityManager.persist(category);
		testEntityManager.persist(pot);
	}

	@Test
	void 추가한_반응을_조회할_때_isDeleted가_false인_것만_조회된다() {
		// given
		List<Reaction> reactionList = List.of(
			ReactionTestUtil.create(pot, user, REACTION_TYPE, false),
			ReactionTestUtil.create(pot, user, REACTION_TYPE, true),
			ReactionTestUtil.create(pot, user, REACTION_TYPE, true),
			ReactionTestUtil.create(pot, user, REACTION_TYPE, false),
			ReactionTestUtil.create(pot, user, REACTION_TYPE, false)
		);
		reactionRepository.saveAll(reactionList);
		testEntityManager.flush();

		// when
		List<Reaction> actualReactionList = reactionRepository.findAll();

		// then
		assertThat(actualReactionList.size()).isEqualTo(3);
	}
}