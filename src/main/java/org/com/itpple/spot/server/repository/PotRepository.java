package org.com.itpple.spot.server.repository;

import java.util.List;
import org.com.itpple.spot.server.entity.Pot;
import org.locationtech.jts.geom.Polygon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PotRepository extends JpaRepository<Pot, Long> {

    @Query(value = "SELECT p FROM Pot p WHERE st_intersects(p.location,:polygon) = true AND p.expiredAt > now() AND p.category.id = :categoryId")
    List<Pot> findAllByLocationWithin(@Param("polygon") Polygon polygon,
            @Param("categoryId") Long categoryId);
}
