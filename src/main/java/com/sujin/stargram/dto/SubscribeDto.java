package com.sujin.stargram.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@NoArgsConstructor

@Data
public class SubscribeDto {
    private BigInteger id;
    private String username;
    private String profileImageUrl;
    private Integer subscribeState;
    private Integer equalUserState;

    public SubscribeDto(BigInteger id, String username,String profileImageUrl,
                        Integer subscribeState, Integer equalUserState) {
        this.id = id;
        this.username = username;
        this.profileImageUrl = profileImageUrl;
        this.subscribeState = subscribeState;
        this.equalUserState = equalUserState;
    }

}
