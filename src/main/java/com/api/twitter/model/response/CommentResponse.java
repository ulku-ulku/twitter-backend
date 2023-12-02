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
public class CommentResponse {
    private String id;
    private String username;
    private String userId;
    private String comment;
    private String postId;
    private Date createdAt;
    private byte[] profileImage;
}
