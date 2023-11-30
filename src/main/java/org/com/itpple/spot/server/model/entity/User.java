package org.com.itpple.spot.server.model.entity;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.com.itpple.spot.server.model.Gender;
import org.com.itpple.spot.server.model.Role;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Entity(name = "sp_user")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User extends BasicDateEntity {

    private Long memberId;
    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "login_type", length = 100) //나중에 nullable = false로 변경
    private String loginType;

    @Column(name = "social_id") //나중에 nullable = false로 변경
    private String socialId;

    @Enumerated(EnumType.STRING)
    @Column(name = "role") //나중에 nullable = false로 변경
    private Role role;

    @Column(name = "name")
    private String name;

    @Column(name = "profile_image_url")
    private String profileImageUrl;

    @Column(name = "phonen_number", length = 100)
    private String phoneNumber;

    @Column(name = "nickname", length = 100)
    private String nickname;

    @Column(name = "birth_day")
    private Date birthDay;

    @Column(name = "gender", length = 10)
    private Gender gender;

    @Column(name = "mbti", length = 10)
    private String mbti;

    @ElementCollection
    @Column(name = "interest", length = 10)
    private List<String> interests = new ArrayList<>();

    @Builder
    public User(Long memberId,String phoneNumber,String nickname,Gender gender,Date birthDay,String mbti,List interests) {
        this.memberId=memberId;
        this.role= Role.USER;
        this.phoneNumber=phoneNumber;
        this.nickname=nickname;
        this.gender=gender;
        this.birthDay=birthDay;
        this.mbti=mbti;
        this.interests=interests;

    }

}
