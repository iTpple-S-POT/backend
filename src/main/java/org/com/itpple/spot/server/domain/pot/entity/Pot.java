package org.com.itpple.spot.server.domain.pot.entity;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.com.itpple.spot.server.domain.pot.domain.category.entity.Category;
import org.com.itpple.spot.server.domain.pot.domain.hashtag.entity.Hashtag;
import org.com.itpple.spot.server.domain.pot.domain.hashtag.entity.PotHashtag;
import org.com.itpple.spot.server.domain.user.entity.User;
import org.com.itpple.spot.server.global.common.constant.PotType;
import org.com.itpple.spot.server.global.common.entity.BasicDateEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLDeleteAll;
import org.locationtech.jts.geom.Point;

@Entity
@Table(indexes = {
        @Index(name = "idx_location_expiredAt", columnList = "location, expired_at", unique = false),
        @Index(name = "idx_pot_expiredAt", columnList = "pot_id, expired_at", unique = false),
        @Index(name = "idx_expiredAt", columnList = "expired_at", unique = false),
})
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE pot SET is_deleted = true WHERE pot_id = ?")
@DynamicInsert
public class Pot extends BasicDateEntity {

    @Id
    @GeneratedValue
    @Column(name = "pot_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "pot_type", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private PotType potType;

    @Column(name = "content", length = 500)
    private String content;

    @Column(name = "image_key", nullable = false, length = 100)
    private String imageKey;

    @Column(name = "location", nullable = false, columnDefinition = "GEOMETRY(Point, 4326)")
    private Point location;

    @Column(name = "expired_at", nullable = false)
    private LocalDateTime expiredAt;

    @Column(name = "is_deleted", nullable = false, columnDefinition = "boolean default false")
    private boolean isDeleted;

    @OneToMany(mappedBy = "pot", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<PotHashtag> potHashtagList = new ArrayList<>();

    /**
     * viewHistory가 있지만, viewCount를 따로 두는 이유는
     * join을 통해 viewHistory를 가져오는 것보다 viewCount를 가져오는 것이 더 효율적이기 때문이다.
     * */
    @Column(name = "view_count", nullable = false, columnDefinition = "bigint default 0")
    private Long viewCount;

    @PrePersist
    public void prePersist() {
        this.expiredAt = LocalDateTime.now().plusDays(1);
    }


    public void addHashtagList(List<Hashtag> hashtagList) {
        hashtagList.forEach(hashtag -> this.potHashtagList.add(PotHashtag.create(this, hashtag)));
    }

    public void removeHashtagList(List<Hashtag> hashtagList) {
        hashtagList.forEach(hashtag -> this.potHashtagList.removeIf(
                potHashtag -> potHashtag.getHashtag().equals(hashtag)));
    }

    public void addViewCount() {
        this.viewCount++;
    }
}
