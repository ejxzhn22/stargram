package com.sujin.stargram.dto;

import com.sujin.stargram.domain.User;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserUpdateDto {
    @NotBlank
    private String name;
    @NotBlank
    private String password;
    private String website;
    private String bio;
    private String phone;
    private String gender;

    //조금위험, 코드수정 필요함
    public User toEntity() {
        return User.builder()
                .name(name) // 이름 입력도 필수
                .password(password) //패스워드를 기재 안했으면 빈 비밀번호가 디비에 들어감, // validation 체크 해야함
                .website(website)
                .bio(bio)
                .phone(phone)
                .gender(gender)
                .build();
    }
}
