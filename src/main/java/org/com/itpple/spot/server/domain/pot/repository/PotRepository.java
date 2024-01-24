package org.com.itpple.spot.server.domain.pot.repository;

import java.util.List;
import org.com.itpple.spot.server.domain.pot.entity.Pot;
import org.locationtech.jts.geom.Polygon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PotRepository extends JpaRepository<Pot, Long> {

    @Query(value = "SELECT p FROM Pot p "
            + "left join fetch p.potHashtagList ph "
            + "WHERE st_intersects(p.location,:polygon) = true "
            + "AND p.expiredAt > now() "
            + "AND (:categoryId is null or p.category.id = :categoryId) "
            + "AND (:hashtagId is null or ph.hashtag.id = :hashtagId) "
            + "AND p.isDeleted = false ")
    List<Pot> findBySearchCondition(@Param("polygon") Polygon polygon,
            @Param("categoryId") Long categoryId, @Param("hashtagId") Long hashtagId);

    @Query(value = "SELECT p FROM Pot p  "
            + "left join fetch p.potHashtagList ph "
            + "WHERE (:polygon is null or st_intersects(p.location,:polygon) = true) "
            + "AND (:categoryId is null or p.category.id = :categoryId) "
            + "AND (:hashtagId is null or ph.hashtag.id = :hashtagId)")
    List<Pot> findBySearchConditionForAdmin(@Param("polygon") Polygon polygon,
            @Param("categoryId") Long categoryId, @Param("hashtagId") Long hashtagId);

    List<Pot> findByUserId(Long userId);

    @Query(value = "SELECT p FROM Pot p "
            + "WHERE p.user.id in :idList "
            + "AND p.expiredAt > now()")
    List<Pot> findByIdAndNotExpired(@Param("idList") List<Long> idList);

}
