package org.com.itpple.spot.server.domain.location.entity;

import static org.com.itpple.spot.server.global.util.GeometryUtil.createPoint;

import javax.persistence.*;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.com.itpple.spot.server.domain.location.dto.PointDTO;
import org.com.itpple.spot.server.domain.user.entity.User;
import org.com.itpple.spot.server.global.common.entity.BasicDateEntity;
import org.hibernate.annotations.DynamicInsert;
import org.locationtech.jts.geom.Point;

@Entity
@Getter
@NoArgsConstructor
@DynamicInsert
public class Location extends BasicDateEntity {
    @Column(name = "location_id")
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    @Column(name = "location")
    private Point location;

    @Builder
    private Location(User user, PointDTO location) {
        this.user = user;
        this.location = createPoint(location);
    }
}