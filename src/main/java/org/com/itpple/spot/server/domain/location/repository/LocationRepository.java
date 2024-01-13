package org.com.itpple.spot.server.domain.location.repository;

import org.com.itpple.spot.server.domain.location.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
