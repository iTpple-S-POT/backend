package org.com.itpple.spot.server.domain.pot.repository;

import java.util.List;
import org.com.itpple.spot.server.domain.pot.entity.Pot;
import org.locationtech.jts.geom.Polygon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PotRepository extends JpaRepository<Pot, Long> {

    @Query(value = "SELECT p FROM Pot p "
            + "WHERE st_intersects(p.location,:polygon) = true "
            + "AND p.expiredAt > now() "
            + "AND (:categoryId is null or p.category.id = :categoryId) "
            + "AND p.isDeleted = false ")
    List<Pot> findByLocationAndCategoryId(@Param("polygon") Polygon polygon,
            @Param("categoryId") Long categoryId);

    @Query(value = "SELECT p FROM Pot p "
            + "WHERE (:polygon is null or st_intersects(p.location,:polygon) = true) "
            + "AND (:categoryId is null or p.category.id = :categoryId) ")
    List<Pot> findByLocationAndCategoryForAdmin(@Param("polygon") Polygon polygon,
            @Param("categoryId") Long categoryId);

    List<Pot> findByUserId(Long userId);

}