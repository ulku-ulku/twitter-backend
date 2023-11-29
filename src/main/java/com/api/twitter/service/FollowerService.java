package com.api.twitter.service;

public interface FollowerService {
    void follow(String followerUserId, String followeeUserId) throws Exception;

    void unfollow(String followerUserId, String followeeUserId) throws Exception;
}
