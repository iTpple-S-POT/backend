package org.com.itpple.spot.server.domain.pot.domain.potReportHistory.repository;

import java.util.List;
import org.com.itpple.spot.server.domain.pot.domain.potReportHistory.entity.PotReportHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PotReportHistoryRepository extends JpaRepository<PotReportHistory, Long> {

    List<PotReportHistory> findAllByPotId(Long potId);

    List<PotReportHistory> findAllByReporterId(Long userId);

    boolean existsByPotIdAndReporterId(Long potId, Long reporterId);


    Long countByPotId(Long potId);
}
