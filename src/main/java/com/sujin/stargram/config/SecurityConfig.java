package com.sujin.stargram.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//시큐리티 설정파일은 WebSecurityConfigurerAdapter 상속
@EnableWebSecurity // 해당 파일로 시큐리티를 활성화한다.
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

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
                .defaultSuccessUrl("/");
    }
}
