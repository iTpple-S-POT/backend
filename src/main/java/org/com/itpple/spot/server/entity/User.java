package org.com.itpple.spot.server.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.com.itpple.spot.server.constant.*;
import org.hibernate.annotations.DynamicInsert;

@Slf4j
@Entity(name = "user_")
@Builder
@Getter
@DynamicInsert
@AllArgsConstructor
@NoArgsConstructor
public class User extends BasicDateEntity {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "login_type", length = 100)
    private String loginType;

    @Column(name = "social_id")
    private String socialId;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
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
    private LocalDate birthDay;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", length = 10)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "mbti", length = 10)
    private Mbti mbti;

    @ElementCollection
    @Column(name = "interest", length = 10)
    private List<Interest> interests = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    public void setStatus(Status status) {
        this.status = status;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public void setBirthDay(LocalDate birthDay) {
        this.birthDay = birthDay;
    }
    public void setGender(Gender gender) {
        this.gender = gender;
    }
    public void setMbti(Mbti mbti) {
        this.mbti = mbti;
    }
    public void setInterests(List<Interest> interests) {
        this.interests = interests;
    }
    @Builder
    public User(String phoneNumber, String nickname, Gender gender, LocalDate birthDay, Mbti mbti, List interests) {
        this.role = Role.USER;
        this.phoneNumber = phoneNumber;
        this.nickname = nickname;
        this.gender = gender;
        this.birthDay = birthDay;
        this.mbti = mbti;
        this.interests = interests;
        this.status = Status.PROGRESS;
    }
}
