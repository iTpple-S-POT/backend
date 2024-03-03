package org.com.itpple.spot.server.domain.pot.domain.potReportHistory.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.com.itpple.spot.server.global.util.GeometryUtil.createPoint;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.com.itpple.spot.server.domain.location.dto.PointDTO;
import org.com.itpple.spot.server.domain.pot.domain.category.entity.Category;
import org.com.itpple.spot.server.domain.pot.domain.potReportHistory.entity.PotReportHistory;
import org.com.itpple.spot.server.domain.pot.entity.Pot;
import org.com.itpple.spot.server.domain.user.entity.User;
import org.com.itpple.spot.server.global.common.constant.PotType;
import org.com.itpple.spot.server.global.common.constant.ReportType;
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
public class PotReportHistoryRepositoryTest {

    final User reporter = User.builder().socialId("test_1234").role(Role.USER).build();
    final User reporter2 = User.builder().socialId("test_12345").role(Role.USER).build();
    final User target = User.builder().socialId("test_1234").role(Role.USER).build();
    final Category category = Category.builder().name("test").build();
    final Pot pot1 = Pot.builder().user(target).potType(PotType.IMAGE).category(category)
            .imageKey("test1").content("test_content1").location(createPoint(new PointDTO(2.0, 2.0)))
            .expiredAt(LocalDateTime.now().plusDays(1))
            .build();
    final Pot pot2 = Pot.builder().user(target).potType(PotType.IMAGE).category(category)
            .imageKey("test2").content("test_content2").location(createPoint(new PointDTO(2.0, 2.0)))
            .expiredAt(LocalDateTime.now().plusDays(1))
            .build();

    private final TestEntityManager entityManager;
    private final PotReportHistoryRepository potReportHistoryRepository;

    @BeforeEach
    void setup() {
        entityManager.persist(reporter);
        entityManager.persist(reporter2);
        entityManager.persist(target);
        entityManager.persist(category);
        entityManager.persist(pot1);
        entityManager.persist(pot2);
    }

    @Test
    public void POT_REPORT_HISTORY_저장하기() {
        //given
        final PotReportHistory potReportHistory = PotReportHistory.builder().reporterId(reporter.getId())
                .potId(pot1.getId()).build();
        // when
        final var result = potReportHistoryRepository.save(potReportHistory);
        // then
        assertThat(result).isNotNull();
    }

    @Test
    public void POT_REPORT_HISTORY_리스트_조회하기_팟기준() {
        //given
        final PotReportHistory potReportHistory = PotReportHistory.builder().reporterId(reporter.getId())
                .potId(pot1.getId()).reportType(ReportType.OTHER).build();
        final PotReportHistory potReportHistory2 = PotReportHistory.builder().reporterId(reporter2.getId())
                .potId(pot1.getId()).reportType(ReportType.OTHER).build();
        entityManager.persist(potReportHistory);
        entityManager.persist(potReportHistory2);
        // when
        final var result = potReportHistoryRepository.findAllByPotId(pot1.getId());
        // then
        assertThat(result.size()).isEqualTo(2);
    }
    @Test
    public void POT_REPORT_HISTORY_리스트_조회하기_리포터기준() {
        //given
        final PotReportHistory potReportHistory = PotReportHistory.builder().reporterId(reporter.getId())
                .potId(pot1.getId()).reportType(ReportType.OTHER).build();
        final PotReportHistory potReportHistory2 = PotReportHistory.builder().reporterId(reporter.getId())
                .potId(pot2.getId()).reportType(ReportType.OTHER).build();
        entityManager.persist(potReportHistory);
        entityManager.persist(potReportHistory2);
        // when
        final var result = potReportHistoryRepository.findAllByReporterId(reporter.getId());
        // then
        assertThat(result.size()).isEqualTo(2);
    }

    //existsByPotIdAndReporterId
    @Test
    public void POT_REPORT_HISTORY_존재여부_조회하기() {
        //given
        final PotReportHistory potReportHistory = PotReportHistory.builder().reporterId(reporter.getId())
                .potId(pot1.getId()).reportType(ReportType.OTHER).build();
        entityManager.persist(potReportHistory);
        // when
        final var result = potReportHistoryRepository.existsByPotIdAndReporterId(pot1.getId(), reporter.getId());
        // then
        assertThat(result).isTrue();
    }

}
