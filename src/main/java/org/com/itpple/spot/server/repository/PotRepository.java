package org.com.itpple.spot.server.repository;

import feign.Param;
import java.util.List;
import org.com.itpple.spot.server.entity.Pot;
import org.locationtech.jts.geom.Polygon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PotRepository extends JpaRepository<Pot, Long> {

    @Query(value = "SELECT p FROM Pot p WHERE st_intersects(p.location,:polygon) = true")
    List<Pot>  findAllByLocationWithin(@Param("polygon") Polygon polygon);
}
