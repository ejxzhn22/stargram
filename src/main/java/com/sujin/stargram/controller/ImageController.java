package com.sujin.stargram.controller;

import com.sujin.stargram.Service.ImageService;
import com.sujin.stargram.config.auth.PrincipalDetails;
import com.sujin.stargram.domain.Image;
import com.sujin.stargram.dto.ImageUploadDto;
import com.sujin.stargram.handler.ex.CustomValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @GetMapping({"/","/image/story"})
    public String story(){
        return "image/story";
    }

    @GetMapping("/image/popular")
    public String popular(Model model){

        List<Image> images = imageService.popular();

        model.addAttribute("images", images);
        return "image/popular";
    }

    @GetMapping("/image/upload")
    public String upload(){
        return "image/upload";
    }

    @PostMapping("/image")
    public String imageUpload(ImageUploadDto imageUploadDto, @AuthenticationPrincipal PrincipalDetails principalDetails) {

        if(imageUploadDto.getFile().isEmpty()){
            throw new CustomValidationException("이미지가 첨부되지 않습니다.", null);
        }
        //서비스 호출
        imageService.uploadImage(imageUploadDto, principalDetails);
        return "redirect:/user/" + principalDetails.getUser().getId();

    }
}
