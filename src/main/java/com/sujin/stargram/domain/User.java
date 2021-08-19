package com.sujin.stargram.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity //디비에 테이블을 생성
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 번호 증가 전략이 데이터베이스를 따라감
    private Long id;

    @Column(length = 20, unique = true)
    private String userId;  // 아이디
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

    private LocalDateTime createDate;

    @PrePersist //디비에 인서트되기 직전에 실행된다.
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }

}
