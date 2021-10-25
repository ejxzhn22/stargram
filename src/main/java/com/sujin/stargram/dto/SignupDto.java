package com.sujin.stargram.dto;

import com.sujin.stargram.domain.User;
import lombok.Data;

import javax.validation.constraints.*;

@Data
public class SignupDto {
    @Size(max = 20)
    @NotBlank
    @Pattern(regexp="[a-zA-Z0-9]{2,12}", message = "아이디는 영어 또는 숫자로 2~12자리 이내로 입력해주세요.")
    private String username;

    @NotBlank
    @Pattern(regexp="^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{6,12}", message = "비밀번호는 영어, 숫자, 특수문자를 포함해서 6~12자리 이내로 입력해주세요.")
    private String password;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Pattern(regexp="[가-힣]{2,6}", message = "이름은 한글로 2~6자리 이내로 입력해주세요.")
    private String name;

    public User toEntity() {
        return User.builder()
                .username(username)
                .password(password)
                .email(email)
                .name(name)
                .build();

    }
}
