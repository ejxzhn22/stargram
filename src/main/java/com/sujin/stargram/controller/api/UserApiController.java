package com.sujin.stargram.controller.api;

import com.sujin.stargram.Service.ImageService;
import com.sujin.stargram.Service.SubscribeService;
import com.sujin.stargram.Service.UserService;
import com.sujin.stargram.config.auth.PrincipalDetails;
import com.sujin.stargram.domain.Image;
import com.sujin.stargram.domain.User;
import com.sujin.stargram.dto.CMRespDto;
import com.sujin.stargram.dto.SubscribeDto;
import com.sujin.stargram.dto.UserProfileDto;
import com.sujin.stargram.dto.UserUpdateDto;
import com.sujin.stargram.handler.ex.CustomValidationApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;
    private final SubscribeService subscribeService;
    private final ImageService imageService;

    @PutMapping("/api/user/{principalId}/profileImageUrl")
    public ResponseEntity<?> profileImageUrlUpdate(@PathVariable long principalId, MultipartFile profileImageFile, @AuthenticationPrincipal PrincipalDetails principalDetails){
        User userEntity = userService.profileImageUpdate(principalId,profileImageFile);
        principalDetails.setUser(userEntity);  // 세션 변경
        return new ResponseEntity<>(new CMRespDto<>(1, "프로필사진변경 성공", null), HttpStatus.OK);
    }

    @GetMapping("/api/user/{pageUserId}/subscribe")
    public ResponseEntity<?> subscribeList(@PathVariable long pageUserId, @AuthenticationPrincipal PrincipalDetails principalDetails){
        List<SubscribeDto> subscribeDto = subscribeService.subscribeList(principalDetails.getUser().getId(), pageUserId);

        return new ResponseEntity<>(new CMRespDto<>(1,"구독자 정보 리스트 불러오기 성공", subscribeDto), HttpStatus.OK);
    }

    @PutMapping("/api/user/{id}")
    public CMRespDto<?> update(@PathVariable long id,
                               @Valid UserUpdateDto userUpdateDto,
                               BindingResult bindingResult, // @Valid가 적혀있는 파라미터 다음에 적어야함
                               @AuthenticationPrincipal PrincipalDetails principalDetails) {

//        if(bindingResult.hasErrors()) {
//            Map<String, String> errorMap = new HashMap<>();
//
//            for(FieldError error : bindingResult.getFieldErrors()) {
//                errorMap.put(error.getField(), error.getDefaultMessage());
////                System.out.println("==========================");
////                System.out.println(error.getDefaultMessage());
////                System.out.println("==========================");
//            }
//            throw new CustomValidationApiException("유효성 검사 실패함", errorMap);
//        }else{
            User userEntity = userService.update(id, userUpdateDto.toEntity());
            principalDetails.setUser(userEntity); //세션정보 변경
            return new CMRespDto<>(1, "회원수정완료", userEntity);
            // 응답시에 userEntity의 모든 getter 함수가 호출되고 JSON으로 파싱하여 응답


    }

    @GetMapping("/api/user/search/{keyword}")
    public ResponseEntity<?> search(@PathVariable String keyword, @AuthenticationPrincipal PrincipalDetails principalDetails ){
        List<UserProfileDto> userProfileDtos = userService.searchUsername(keyword, principalDetails.getUser().getId());

        return new ResponseEntity<>(new CMRespDto<>(1,"아이디 검색 리스트 불러오기 성공", userProfileDtos), HttpStatus.OK);
    }



}
