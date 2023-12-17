package org.com.itpple.spot.server.entity;


import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.com.itpple.spot.server.constant.PotType;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLDeleteAll;
import org.hibernate.annotations.Where;
import org.locationtech.jts.geom.Point;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE pot SET is_deleted = true WHERE pot_id = ?")
@SQLDeleteAll(sql = "UPDATE pot SET is_deleted = true WHERE pot_id in ?")
@Where(clause = "is_deleted = false")
public class Pot extends BasicDateEntity {

    @Id
    @GeneratedValue
    @Column(name = "pot_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "user_id", insertable = false, updatable = false)
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "category_id", insertable = false, updatable = false)
    private Long categoryId;

    @Column(name = "pot_type", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private PotType potType;

    @Column(name = "content", nullable = false, length = 500)
    private String content;

    @Column(name = "image_key", nullable = false, length = 100)
    private String imageKey;

    @Column(name = "location", nullable = false)
    private Point location;

    @Column(name = "expired_at", nullable = false)
    private LocalDateTime expiredAt;

    @Column(name = "is_deleted", nullable = false, columnDefinition = "boolean default false")
    private boolean isDeleted;

    //TODO: 해시태그 기획이 확정되면 추가
}
