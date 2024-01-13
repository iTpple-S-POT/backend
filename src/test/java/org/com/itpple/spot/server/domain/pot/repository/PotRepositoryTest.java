package org.com.itpple.spot.server.domain.pot.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.com.itpple.spot.server.global.util.GeometryUtil.createCircle;
import static org.com.itpple.spot.server.global.util.GeometryUtil.createPoint;
import static org.com.itpple.spot.server.global.util.GeometryUtil.createPolygon;

import lombok.RequiredArgsConstructor;
import org.com.itpple.spot.server.domain.category.entity.Category;
import org.com.itpple.spot.server.domain.location.dto.PointDTO;
import org.com.itpple.spot.server.domain.pot.entity.Pot;
import org.com.itpple.spot.server.domain.user.entity.User;
import org.com.itpple.spot.server.global.common.constant.PotType;
import org.com.itpple.spot.server.global.common.constant.Role;
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
                PotType.IMAGE).location(createPoint(new PointDTO(2.0, 2.0))).build();
        // when
        final var result = potRepository.save(pot);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isNotNull();
        assertThat(result.getUser()).isEqualTo(user);
        assertThat(result.getCategory()).isEqualTo(category);
        assertThat(result.getImageKey()).isEqualTo("test.jpg");
        assertThat(result.getPotType()).isEqualTo(PotType.IMAGE);
        assertThat(result.getLocation()).isEqualTo(createPoint(new PointDTO(2.0, 2.0)));
    }

    @Test
    public void POT_리스트_조회하기() {
        //given
        final Pot pot1 = Pot.builder().user(user).category(category).imageKey("test.jpg").potType(
                PotType.IMAGE).location(createPoint(new PointDTO(2.0, 2.0))).build();
        final Pot pot2 = Pot.builder().user(user).category(category).imageKey("test.jpg").potType(
                PotType.IMAGE).location(createPoint(new PointDTO(2.0, 2.0))).build();
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
                PotType.IMAGE).location(createPoint(new PointDTO(2.0, 2.0))).build();
        final Pot pot2 = Pot.builder().user(user).category(category).imageKey("test.jpg").potType(
                PotType.IMAGE).location(createPoint(new PointDTO(1.0, 3.0))).build();
        final Pot pot3 = Pot.builder().user(user).category(category).imageKey("test.jpg").potType(
                PotType.IMAGE).location(createPoint(new PointDTO(1.0, 4.0))).build();
        entityManager.persist(pot1);
        entityManager.persist(pot2);
        entityManager.persist(pot3);
        entityManager.flush();

        //when
        final var result = potRepository.findByLocationAndCategoryId(createPolygon(new PointDTO[]{
                new PointDTO(1.0, 1.0),
                new PointDTO(1.0, 3.0),
                new PointDTO(3.0, 1.0),
                new PointDTO(3.0, 3.0)
        }), category.getId());

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    public void POT_리스트_조회하기_원_범위() {
        var pointDTO1 = new PointDTO(37.53181382825802, 126.91438309995776);//국회의사당
        var pointDTO2 = new PointDTO(37.531077388272465, 126.91773278201156);//국회도서관(1km 이내)
        var pointDTO3 = new PointDTO( 37.52258059969025, 126.90525326032581);//여의도시장역(1km 밖)
        final Pot pot1 = Pot.builder().user(user).category(category).imageKey("test.jpg").potType(
                PotType.IMAGE).location(createPoint(pointDTO1)).build();
        final Pot pot2 = Pot.builder().user(user).category(category).imageKey("test.jpg").potType(
                PotType.IMAGE).location(createPoint(pointDTO2)).build();
        final Pot pot3 = Pot.builder().user(user).category(category).imageKey("test.jpg").potType(
                PotType.IMAGE).location(createPoint(pointDTO3)).build();
        entityManager.persist(pot1);
        entityManager.persist(pot2);
        entityManager.persist(pot3);
        entityManager.flush();

        //when
        final var result = potRepository.findByLocationAndCategoryId(
                createCircle(pointDTO1, 1000), category.getId());

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    public void POT_리스트_조회하기_조건_무시() {
        final Pot pot1 = Pot.builder().user(user).category(category).imageKey("test.jpg").potType(
                PotType.IMAGE).location(createPoint(new PointDTO(2.0, 2.0))).isDeleted(true).build();
        final Pot pot2 = Pot.builder().user(user).category(category).imageKey("test.jpg").potType(
                PotType.IMAGE).location(createPoint(new PointDTO(2.0, 3.0))).build();
        entityManager.persist(pot1);
        entityManager.persist(pot2);
        entityManager.flush();

        //when
        final var result = potRepository.findByLocationAndCategoryForAdmin(
                null, null);

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
    }
}
