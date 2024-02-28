package org.com.itpple.spot.server.domain.pot.domain.viewHistory.entity;


import javax.persistence.*;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.com.itpple.spot.server.domain.pot.entity.Pot;
import org.com.itpple.spot.server.domain.user.entity.User;
import org.com.itpple.spot.server.global.common.entity.BasicDateEntity;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
public class ViewHistory extends BasicDateEntity {
    @Id
    @Column(name = "view_history_id")
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pot_id")
    private Pot pot;

    public static ViewHistory of(User user, Pot pot) {
        return new ViewHistory(null, user, pot);
    }

}
