package com.api.twitter.service;

import com.api.twitter.entity.Post;
import com.api.twitter.entity.User;
import com.api.twitter.model.response.PostResponse;

import java.util.List;

public interface PostService {
    void post(String content, String userId) throws Exception;

    List<PostResponse> getAllPosts(User loggedInUser) throws Exception;

    PostResponse getById(String postId, User loggedInUser) throws Exception;
}
