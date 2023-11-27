package org.com.itpple.spot.server.domain.member.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor         //lombok 생성자 자동으로 만듦 //ctrl+f12로 확인
public class Member {

    @Column(name = "phonenumber", length = 100)
    private String phonenumber;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 100) //카카오에서 받은 이름
    private String name;

    //@NotNull //나중에 수정
    @Column(name = "login_type", length = 100)
    private String login_type;

    //@NotNull //나중에 수정
    @Column(name = "social_id", length = 100)
    private String social_id;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "nickname", length = 100)
    private String nickname;

    @Column(name = "profile_image_url")
    private String profile_image_url;

    @Column(name = "birth_day")
    private Date birth_day;

    @Column(name = "gender", length = 10)
    private String gender;

    @Column(name = "mbti", length = 10)
    private String mbti;

    @ElementCollection
    @Column(name = "interest", length = 10)
    private List<String> interests = new ArrayList<>();


    public Member(){}

    @Builder
    public Member(String phonenumber,String nickname,String gender,Date birth_day,String mbti,List interests) {
        this.role=Role.ROLE_USER;
        this.phonenumber=phonenumber;
        this.nickname=nickname;
        this.gender=gender;
        this.birth_day=birth_day;
        this.mbti=mbti;
        this.interests=interests;

    }
}
