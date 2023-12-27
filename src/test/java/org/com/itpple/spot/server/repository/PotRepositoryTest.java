package org.com.itpple.spot.server.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.com.itpple.spot.server.util.GeometryUtil.createCircle;
import static org.com.itpple.spot.server.util.GeometryUtil.createPoint;
import static org.com.itpple.spot.server.util.GeometryUtil.createPolygon;

import lombok.RequiredArgsConstructor;
import org.com.itpple.spot.server.constant.PotType;
import org.com.itpple.spot.server.constant.Role;
import org.com.itpple.spot.server.dto.Location;
import org.com.itpple.spot.server.entity.Category;
import org.com.itpple.spot.server.entity.Pot;
import org.com.itpple.spot.server.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;

@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
public class PotRepositoryTest {

    final User user = User.builder().socialId("test_1234").role(Role.USER).build();
    final Category category = Category.builder().name("test").build();
    private final TestEntityManager entityManager;
    private final PotRepository potRepository;

    @BeforeEach
    void setup() {
        entityManager.persist(user);
        entityManager.persist(category);
    }

    @Test
    public void POT_저장하기() {
        //given
        final Pot pot = Pot.builder().user(user).category(category).imageKey("test.jpg").potType(
                PotType.IMAGE).location(createPoint(new Location(2.0, 2.0))).build();
        // when
        final var result = potRepository.save(pot);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isNotNull();
        assertThat(result.getUser()).isEqualTo(user);
        assertThat(result.getCategory()).isEqualTo(category);
        assertThat(result.getImageKey()).isEqualTo("test.jpg");
        assertThat(result.getPotType()).isEqualTo(PotType.IMAGE);
        assertThat(result.getLocation()).isEqualTo(createPoint(new Location(2.0, 2.0)));
    }

    @Test
    public void POT_리스트_조회하기() {
        //given
        final Pot pot1 = Pot.builder().user(user).category(category).imageKey("test.jpg").potType(
                PotType.IMAGE).location(createPoint(new Location(2.0, 2.0))).build();
        final Pot pot2 = Pot.builder().user(user).category(category).imageKey("test.jpg").potType(
                PotType.IMAGE).location(createPoint(new Location(2.0, 2.0))).build();
        entityManager.persist(pot1);
        entityManager.persist(pot2);
        entityManager.flush();

        //when
        final var result = potRepository.findAll();

        //then
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    public void POT_리스트_조회하기_직사각형_범위() {
        final Pot pot1 = Pot.builder().user(user).category(category).imageKey("test.jpg").potType(
                PotType.IMAGE).location(createPoint(new Location(2.0, 2.0))).build();
        final Pot pot2 = Pot.builder().user(user).category(category).imageKey("test.jpg").potType(
                PotType.IMAGE).location(createPoint(new Location(1.0, 3.0))).build();
        final Pot pot3 = Pot.builder().user(user).category(category).imageKey("test.jpg").potType(
                PotType.IMAGE).location(createPoint(new Location(1.0, 4.0))).build();
        entityManager.persist(pot1);
        entityManager.persist(pot2);
        entityManager.persist(pot3);
        entityManager.flush();

        //when
        final var result = potRepository.findAllByLocationWithin(createPolygon(new Location[]{
                new Location(1.0, 1.0),
                new Location(1.0, 3.0),
                new Location(3.0, 1.0),
                new Location(3.0, 3.0)
        }), category.getId());

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    public void POT_리스트_조회하기_원_범위() {
        final Pot pot1 = Pot.builder().user(user).category(category).imageKey("test.jpg").potType(
                PotType.IMAGE).location(createPoint(new Location(2.0, 2.0))).build();
        final Pot pot2 = Pot.builder().user(user).category(category).imageKey("test.jpg").potType(
                PotType.IMAGE).location(createPoint(new Location(2.0, 3.0))).build();
        final Pot pot3 = Pot.builder().user(user).category(category).imageKey("test.jpg").potType(
                PotType.IMAGE).location(createPoint(new Location(1.0, 3.0))).build();
        entityManager.persist(pot1);
        entityManager.persist(pot2);
        entityManager.persist(pot3);
        entityManager.flush();

        //when
        final var result = potRepository.findAllByLocationWithin(
                createCircle(new Location(2.0, 2.0), 1.0), category.getId());

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
    }

}
