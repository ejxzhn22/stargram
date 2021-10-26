package com.sujin.stargram.config.auth;

import com.sujin.stargram.domain.User;
import com.sujin.stargram.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


// 시큐리티에 설정한 로그인 url을 요청하면 PrincipalDetailsService(UserDetailsService)가 로그인을 진행
// 그래서 PrincipalDetailsService에  loadUserByUsername 메소드가 실행됨
// 패스워드는 알아서 체킹해주기 때문에 신경쓸 필요 없음
// 리턴타입은 UserDetails => 리턴이 잘되면 UserDetails타입을 세션으로 자동으로 만들어줌
// 세션안에 SecurityContextHolder 안에 Authentication 안에 PrincipalDetails 타입의 user
// 너무 복잡 => 어노테이션 있음 => @AuthenticationPrincipal : Authentication으로 바로 접근
@RequiredArgsConstructor
@Service
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User userEntity = userRepository.findByUsername(username);

        if(userEntity != null){
            return new PrincipalDetails(userEntity);
        }
        return null;
    }
}
