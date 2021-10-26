package com.sujin.stargram.config;

import com.sujin.stargram.config.oauth.PrincipalOauth2UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//시큐리티 설정파일은 WebSecurityConfigurerAdapter 상속
@EnableWebSecurity // 해당 파일로 시큐리티를 활성화한다.
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //1.코드받기(인증), 2. 엑세스토큰(권한)
    //3. 사용자프로필 정보를 가져오고
    //4-1 그 정보를 토대로 회원가입을 자동으로 진행시키기도함
    //4-2 (이메일 전화번호 이름 아이디) 쇼핑몰 -> (집주소), 백화점몰 -> (vip등급 , 일반등급)


    private final PrincipalOauth2UserService principalOauth2UserService;

    public SecurityConfig(@Lazy PrincipalOauth2UserService principalOauth2UserService) {
        this.principalOauth2UserService = principalOauth2UserService;
    }

    @Bean
    public BCryptPasswordEncoder encode() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        //super.configure(web); => 삭제하면 기존 시큐리티가 가지고 있는 기능이 다 비활성화
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/","/user/**","/image/**","subscribe/**","/comment/**", "/api/**").authenticated()
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/auth/signin")  // get
                .loginProcessingUrl("/auth/signin")  // post 시큐리티가 로그인 프로세스를 해준다.
                .defaultSuccessUrl("/")
                .and()
                .oauth2Login()
                .loginPage("/auth/signin")
                .userInfoEndpoint()
                .userService(principalOauth2UserService);
        // 구글로그인이 완료된 뒤에 후처리 필요.     Tip 코드 x (엑세스토큰 + 사용자 프로필정보 o)
    }
}
