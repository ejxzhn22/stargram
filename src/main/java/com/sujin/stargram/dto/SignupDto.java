package com.sujin.stargram.dto;

import com.sujin.stargram.domain.User;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class SignupDto {
    @Size(max = 20)
    @NotBlank
    private String userId;
    @NotBlank
    private String password;
    @NotBlank
    private String email;
    @NotBlank
    private String name;

    public User toEntity() {
        return User.builder()
                .userId(userId)
                .password(password)
                .email(email)
                .name(name)
                .build();

    }
}
