package org.com.itpple.spot.server.repository;

import org.com.itpple.spot.server.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
