package com.sujin.stargram.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

//@NotBlank// 빈값이거나 null, 빈 공백 체크
//@NotEmpty //빈값이거나 null  체크
//@NotNull //null 체크

@Data
public class CommentDto {
    @NotBlank// 빈값이거나 null, 빈 공백 체크
    private String content;
    @NotNull //null 체크
    private Long imageId;

    //toEntity 필요 x
}
