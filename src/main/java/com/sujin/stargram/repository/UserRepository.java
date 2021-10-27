package com.sujin.stargram.repository;

import com.sujin.stargram.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//JpaRepository 상속해서 어노테이션이 없어도 IoC등록이 자동으로 된다.
public interface UserRepository extends JpaRepository<User, Long> { //<타입, pk타입>
    //JPA query method
    User findByUsername(String username);

    //아이디 검색
    List<User> findByUsernameContaining(String username);


}
