package com.api.twitter.service;

public interface LikeService {
    void like(String postId, String userId);

    void removeLike(String postId, String userId);
}
