package com.sujin.stargram.dto;

import com.sujin.stargram.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserProfileDto {

    private boolean pageOwner;
    private int imageCount;
    private boolean subscribeState;
    private int subscribeCount;
    private User user;
}
