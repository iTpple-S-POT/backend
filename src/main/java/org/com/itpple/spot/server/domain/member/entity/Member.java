package org.com.itpple.spot.server.domain.member.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
public class Member {

    @Column(name = "phonenNumber", length = 100)
    private String phoneNumber;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 100) //카카오에서 받은 이름
    private String name;

    //@NotNull //나중에 수정
    @Column(name = "loginType", length = 100)
    private String loginType;

    //@NotNull //나중에 수정
    @Column(name = "socialId", length = 100)
    private String socialId;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "nickname", length = 100)
    private String nickname;

    @Column(name = "profileImageUrl")
    private String profileImageUrl;

    @Column(name = "birthDay")
    private Date birthDay;

    @Column(name = "gender", length = 10)
    private String gender;

    @Column(name = "mbti", length = 10)
    private String mbti;

    @ElementCollection
    @Column(name = "interest", length = 10)
    private List<String> interests = new ArrayList<>();

    public Member(){}

    @Builder
    public Member(String phoneNumber,String nickname,String gender,Date birthDay,String mbti,List interests) {
        this.role=Role.ROLE_USER;
        this.phoneNumber=phoneNumber;
        this.nickname=nickname;
        this.gender=gender;
        this.birthDay=birthDay;
        this.mbti=mbti;
        this.interests=interests;

    }
}
