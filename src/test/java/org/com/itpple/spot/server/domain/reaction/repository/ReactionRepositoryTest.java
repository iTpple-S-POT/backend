package org.com.itpple.spot.server.domain.reaction.repository;

import lombok.RequiredArgsConstructor;
import org.com.itpple.spot.server.domain.pot.domain.category.entity.Category;
import org.com.itpple.spot.server.domain.pot.entity.Pot;
import org.com.itpple.spot.server.domain.reaction.dto.ReactionTypeCount;
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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import static org.springframework.test.context.TestConstructor.AutowireMode;

@RequiredArgsConstructor
@TestConstructor(autowireMode = AutowireMode.ALL)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
@DataJpaTest
class ReactionRepositoryTest {

    private final ReactionRepository sut;
    private final TestEntityManager testEntityManager;

    private final User user = UserTestUtil.create();
    private final Category category = PotTestUtil.createCategory();
    private final Pot pot = PotTestUtil.create(user, category);

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
            ReactionTestUtil.create(pot, user, ReactionType.GOOD, false),
            ReactionTestUtil.create(pot, user, ReactionType.GOOD, true),
            ReactionTestUtil.create(pot, user, ReactionType.GOOD, true),
            ReactionTestUtil.create(pot, user, ReactionType.GOOD, false),
            ReactionTestUtil.create(pot, user, ReactionType.GOOD, false)
        );
        sut.saveAll(reactionList);
        testEntityManager.flush();

        // when
        List<Reaction> actualReactionList = sut.findAll();

        // then
        assertThat(actualReactionList.size()).isEqualTo(3);
    }

    @Test
    void ReactionType을_그룹화해_각_반응_타입과_개수를_반환한다() {
        // given
        List<Reaction> reactionList = List.of(
            ReactionTestUtil.create(pot, user, ReactionType.SMILE, false),
            ReactionTestUtil.create(pot, user, ReactionType.SAD, false),
            ReactionTestUtil.create(pot, user, ReactionType.ANGRY, false),
            ReactionTestUtil.create(pot, user, ReactionType.ANGRY, false),
            ReactionTestUtil.create(pot, user, ReactionType.HEART, false),
            ReactionTestUtil.create(pot, user, ReactionType.HEART, false),
            ReactionTestUtil.create(pot, user, ReactionType.HEART, false),
            ReactionTestUtil.create(pot, user, ReactionType.GOOD, false),
            ReactionTestUtil.create(pot, user, ReactionType.GOOD, false),
            ReactionTestUtil.create(pot, user, ReactionType.GOOD, false)
        );
        sut.saveAll(reactionList);
        testEntityManager.flush();

        // when
        List<ReactionTypeCount> reactionTypeCountList = sut.countEachReactionType(pot.getId());

        // then
        for (ReactionTypeCount reactionTypeCount : reactionTypeCountList) {
            switch (reactionTypeCount.getReactionType()) {
                case SMILE, SAD -> assertThat(reactionTypeCount.getCount()).isEqualTo(1);
                case ANGRY -> assertThat(reactionTypeCount.getCount()).isEqualTo(2);
                case HEART, GOOD -> assertThat(reactionTypeCount.getCount()).isEqualTo(3);
            }
        }
    }
}