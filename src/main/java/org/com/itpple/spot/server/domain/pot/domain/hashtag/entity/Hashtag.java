package org.com.itpple.spot.server.domain.pot.domain.hashtag.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.com.itpple.spot.server.global.common.entity.BasicDateEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.SQLDelete;

@Slf4j
@Entity
@Table(indexes = {
    @Index(name = "hashtag_hashtag_idx", columnList = "hashtag, count", unique = true)
})
@Getter
@DynamicInsert
@AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE hashtag SET is_deleted = true WHERE hashtag_id = ?")
public class Hashtag extends BasicDateEntity {
    @Column(name = "hashtag_id")
    @Id
    private Long id;

    @Column(length = 50, nullable = false, updatable = false)
    private String hashtag;

    @Column(nullable = false, columnDefinition = "bigint default 0")
    private Long count;

//    @Column(name = "is_deleted", columnDefinition = "boolean default false")
    private Boolean isDeleted;


    public static Hashtag newInstance(String hashtag) {
        return new Hashtag(null, hashtag, 0L, false);
    }

    public void delete() {
        this.isDeleted = true;
    }

    public void increaseCount() {
        this.count++;
    }


}
