package org.com.itpple.spot.server.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLDeleteAll;
import org.hibernate.annotations.Where;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE category SET is_deleted = true WHERE category_id = ?")
@SQLDeleteAll(sql = "UPDATE category SET is_deleted = true WHERE category_id in ?")
@Where(clause = "is_deleted = false")

public class Category extends BasicDateEntity {

    @Column(name = "category_id")
    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 200)
    private String description;

    @Column(name = "is_deleted")
    private boolean isDeleted;
}
