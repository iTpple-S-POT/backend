package org.com.itpple.spot.server.domain.pot.domain.viewHistory.repository;

import java.util.List;
import org.com.itpple.spot.server.domain.pot.domain.viewHistory.entity.ViewHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ViewHistoryRepository extends JpaRepository<ViewHistory, Long> {

    @Query("select v from ViewHistory v where v.user.id = :userId order by v.createdAt desc")
    List<ViewHistory> findAllByUserIdOrderByCreatedAtDesc(Long userId);

    boolean existsByPotIdAndUserId(Long userId, Long potId);

    List<ViewHistory> findByUserId(Long userId);
}
