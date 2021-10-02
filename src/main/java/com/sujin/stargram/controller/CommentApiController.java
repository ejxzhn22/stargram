package com.sujin.stargram.controller;

import com.sujin.stargram.Service.CommentService;
import com.sujin.stargram.config.auth.PrincipalDetails;
import com.sujin.stargram.domain.Comment;
import com.sujin.stargram.dto.CMRespDto;
import com.sujin.stargram.dto.CommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentApiController {

    private final CommentService commentService;

    @PostMapping("/api/comment")
    public ResponseEntity<?> commentSave(@RequestBody CommentDto commentDto, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Comment comment = commentService.newComment(commentDto.getContent(), commentDto.getImageId(), principalDetails.getUser().getId());

        return new ResponseEntity<>(new CMRespDto<>(1,"댓글쓰기 성공", comment), HttpStatus.CREATED);
    }

    @DeleteMapping("/api/comment/{id}")
    public ResponseEntity<?> commentDelete(@PathVariable long id) {

        return null;
    }
}
