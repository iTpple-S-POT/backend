package org.com.itpple.spot.server.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.com.itpple.spot.server.model.Role;

@Slf4j
@Entity(name = "sp_user")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User extends BasicDateEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "o_auth_id", nullable = false)
    private String oAuthId;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "profile_image_url")
    private String profileImageUrl;
}