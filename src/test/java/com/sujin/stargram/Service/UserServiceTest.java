package com.sujin.stargram.Service;

import com.sujin.stargram.domain.User;
import com.sujin.stargram.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired UserRepository userRepository;

    @Test
    public void 회원가입() {
        //GIVEN
        User user = new User();
        user.setUsername("aaa");
        user.setPassword("1234");
        user.setEmail("user@user.com");
        user.setName("수진");
        user.setRole("ROLE_USER");

        //WHEN
        User savedUser = userRepository.save(user);

        //THEN
        Assertions.assertThat(user).isEqualTo(savedUser);
    }
}