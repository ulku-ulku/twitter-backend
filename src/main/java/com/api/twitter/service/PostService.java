package com.api.twitter.service;

import com.api.twitter.model.response.PostResponse;

import java.util.List;

public interface PostService {
    void post(String content, String userId) throws Exception;

    List<PostResponse> getAllPosts() throws Exception;
}
