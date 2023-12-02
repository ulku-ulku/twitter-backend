package com.api.twitter.service;

import com.api.twitter.model.response.CommentResponse;

import java.util.List;

public interface CommentService {
    void addComment(String comment, String postId, String userId) throws Exception;

    List<CommentResponse> getCommentsByPostId(String postId) throws Exception;
}
