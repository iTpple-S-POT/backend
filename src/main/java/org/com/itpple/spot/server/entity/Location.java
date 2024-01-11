package org.com.itpple.spot.server.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.com.itpple.spot.server.dto.PointDTO;
import org.hibernate.annotations.DynamicInsert;
import org.locationtech.jts.geom.Point;

import javax.persistence.*;

import static org.com.itpple.spot.server.util.GeometryUtil.convertToPoint;

@Entity
@Getter
@NoArgsConstructor
@DynamicInsert
public class Location {
    @Column(name = "location_id")
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "location", nullable = false)
    private Point location;

    @Builder
    private Location(User user, PointDTO location) {
        this.user = user;
        this.location=convertToPoint(location.lat(),location.lon());
    }
}
