package org.com.itpple.spot.server.domain.pot.domain.hashtag.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.com.itpple.spot.server.domain.pot.entity.Pot;

@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class PotHashtag {

    @Column(name = "pot_hashtag_id")
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pot_id", nullable = false)
    private Pot pot;

    @ManyToOne
    @JoinColumn(name = "hashtag_id", nullable = false)
    private Hashtag hashtag;

    public static PotHashtag create(Pot pot, Hashtag hashtag) {
        return new PotHashtag(null, pot, hashtag);
    }
}
