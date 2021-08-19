package com.sujin.stargram.config.auth;

import com.sujin.stargram.domain.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Data
public class PrincipalDetails implements UserDetails {

    private User user;

    public PrincipalDetails(User user){
        this.user = user;
    }

    //권한 => 한개가 아닐 수 있음
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        // 자바는 매개변수에 함수를 넣을수 없다. -> 일급객체가 아니기 떄문
        // 그래서 인터페이스를 넣음
        collection.add(() ->{
                return user.getRole();
        });
        return collection;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //계정이 잠겼는지
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //계정 비밀번호가 오래됬나
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정 활성화가 되어있는지
    @Override
    public boolean isEnabled() {
        return true;
    }
}
