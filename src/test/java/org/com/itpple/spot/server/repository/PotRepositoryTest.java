package org.com.itpple.spot.server.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.com.itpple.spot.server.util.GeometryUtil.convertToPoint;

import lombok.RequiredArgsConstructor;
import org.com.itpple.spot.server.constant.PotType;
import org.com.itpple.spot.server.constant.Role;
import org.com.itpple.spot.server.entity.Category;
import org.com.itpple.spot.server.entity.Pot;
import org.com.itpple.spot.server.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;

@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
@DataJpaTest
@ActiveProfiles("test")
public class PotRepositoryTest {
    private final TestEntityManager entityManager;

    private final PotRepository potRepository;

    final User user = User.builder().socialId("test_1234").role(Role.USER).build();
    final Category category = Category.builder().name("test").build();

    @Test
    public void POT리스트가져오기() {
        //given
        final Pot pot = Pot.builder().user(user).category(category).imageKey("test.jpg").potType(
                PotType.IMAGE).location(convertToPoint(2.0, 2.0)).build();
        entityManager.persist(user);
        entityManager.persist(category);
        potRepository.save(pot);

        // when
        final var result = potRepository.findAll();

        // then
        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(1);
    }

}
