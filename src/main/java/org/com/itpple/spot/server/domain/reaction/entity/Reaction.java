package org.com.itpple.spot.server.domain.reaction.entity;

import javax.persistence.*;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.com.itpple.spot.server.domain.user.entity.User;
import org.com.itpple.spot.server.domain.pot.entity.Pot;
import org.com.itpple.spot.server.global.common.constant.ReactionType;
import org.com.itpple.spot.server.global.common.entity.BasicDateEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@DynamicInsert
@SQLDelete(sql = "UPDATE reaction SET is_deleted = true WHERE reaction_id = ?")
@Where(clause = "is_deleted = false")
@Entity
public class Reaction extends BasicDateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reaction_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pot_id", nullable = false)
    private Pot pot;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "reaction_type", nullable = false)
    private ReactionType reactionType;

    @Column(name = "is_deleted", nullable = false, columnDefinition = "boolean default false")
    private boolean isDeleted;

    @Builder
    private Reaction(Pot pot, User user, ReactionType reactionType) {
        this.pot = pot;
        this.user = user;
        this.reactionType = reactionType;
    }

    public void updateIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}
