package com.sujin.stargram.dto;

import lombok.Data;

@Data
public class CommentDto {

    private String content;
    private long imageId;

    //toEntity 필요 x
}
