package org.com.itpple.spot.server.domain.location.repository;

import org.com.itpple.spot.server.domain.location.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Long> {
    Optional<Location> findById(Long userId);

    @Query("SELECT l FROM Location l WHERE l.user.id = :userId ORDER BY l.createdAt DESC")
    Optional<List<Location>> findByUserId(@Param("userId") Long userId);
}
