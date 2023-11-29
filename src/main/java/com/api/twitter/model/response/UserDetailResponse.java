package com.api.twitter.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailResponse {
    private String id;
    private String email;
    private String username;
    private Date registeredDate;
    private String bio;
    private byte[] profileImage;
    private byte[] bannerImage;
    private boolean isFollowing;
    private long followers;
    private long followees;
}
