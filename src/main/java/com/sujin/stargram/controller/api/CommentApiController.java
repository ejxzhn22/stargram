package com.sujin.stargram.controller.api;

import com.sujin.stargram.Service.CommentService;
import com.sujin.stargram.config.auth.PrincipalDetails;
import com.sujin.stargram.domain.Comment;
import com.sujin.stargram.dto.CMRespDto;
import com.sujin.stargram.dto.CommentDto;
import com.sujin.stargram.handler.ex.CustomValidationApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CommentApiController {

    private final CommentService commentService;

    @PostMapping("/api/comment")
    public ResponseEntity<?> commentSave(@Valid @RequestBody CommentDto commentDto, BindingResult bindingResult, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Comment comment = commentService.newComment(commentDto.getContent(), commentDto.getImageId(), principalDetails.getUser().getId());

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
//        }

        return new ResponseEntity<>(new CMRespDto<>(1,"댓글쓰기 성공", comment), HttpStatus.CREATED);
    }

    @DeleteMapping("/api/comment/{id}")
    public ResponseEntity<?> commentDelete(@PathVariable long id) {
        commentService.deleteComment(id);
        return new ResponseEntity<>(new CMRespDto<>(1,"댓글 삭제 성공", null), HttpStatus.OK);
    }
}
