package com.sujin.stargram.controller;

import com.sujin.stargram.Service.UserService;
import com.sujin.stargram.config.auth.PrincipalDetails;
import com.sujin.stargram.domain.User;
import com.sujin.stargram.dto.UserProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/user/{pageUserId}")
    public String profile(@PathVariable long pageUserId, Model model, @AuthenticationPrincipal PrincipalDetails principalDetails){
        UserProfileDto dto = userService.userProfile(pageUserId,principalDetails.getUser().getId());
        model.addAttribute("dto", dto);
        return "user/profile";
    }

    @GetMapping("/user/{id}/update")
    public String update(@PathVariable long id, @AuthenticationPrincipal PrincipalDetails principalDetails){
        //System.out.println("세션정보: " + principalDetails.getUser());

        // 직접 찾기
        // Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // PrincipalDetails mPrincipalDetails = (PrincipalDetails)auth.getPrincipal();
        // System.out.println("직접 찾은 세션 : " + mPrincipalDetails.getUser());

        //model.addAttribute("principal", principalDetails.getUser());
        return "user/update";
    }
}
