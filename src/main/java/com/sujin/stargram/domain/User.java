package com.sujin.stargram.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity //디비에 테이블을 생성
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 번호 증가 전략이 데이터베이스를 따라감
    private Long id;

    @Column(length = 100, unique = true)
    private String username;  // 아이디
    @Column(nullable = false)
    private String password;  // 비밀번호
    @Column(nullable = false)
    private String email;  //이메일
    @Column(nullable = false)
    private String name;   // 이름

    private String bio;    // 자기소개
    private String website;    // 사이트
    private String phone;    //핸드폰 번호
    private String gender;    // 성별

    private String profileImageUrl;    //프로필 사진
    private String role;    // 권한

    private String provider;
    private String providerId;

    @Builder
    public User(String username, String password, String email, String name, String role, String provider, String providerId, LocalDateTime createDate) {

        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
        this.createDate = createDate;
    }

    // 나는 연관관계 주인 아님, 테이블에 컬럼 안만듦
    // user select할 때 해당 userId로 등록된 이미지를 같이 가져왕
    // OnetoMany는 fetchType 기본  LAZY  - getImages() 할때 가져옴
    // EAGER = USER SELECT할 떄 같이 가져옴
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"user"})
    private List<Image> images;

    private LocalDateTime createDate;

    @PrePersist //디비에 인서트되기 직전에 실행된다.
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", bio='" + bio + '\'' +
                ", website='" + website + '\'' +
                ", phone='" + phone + '\'' +
                ", gender='" + gender + '\'' +
                ", profileImageUrl='" + profileImageUrl + '\'' +
                ", role='" + role + '\'' +
                ", createDate=" + createDate +
                '}';
    }
}
