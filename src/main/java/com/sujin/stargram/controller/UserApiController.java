package com.sujin.stargram.controller;

import com.sujin.stargram.dto.UserUpdateDto;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserApiController {

    @PutMapping("/api/user/{id}")
    public String update(UserUpdateDto userUpdateDto) {
        System.out.println
    }(userUpdateDto);
        return "ok";
}
