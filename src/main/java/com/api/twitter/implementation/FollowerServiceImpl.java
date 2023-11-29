package com.api.twitter.implementation;

import com.api.twitter.entity.Follower;
import com.api.twitter.repository.FollowerRepository;
import com.api.twitter.service.FollowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FollowerServiceImpl implements FollowerService {
    @Autowired
    private FollowerRepository followerRepository;

    @Override
    public void follow(String followerUserId, String followeeUserId) throws Exception {
        Follower follower = Follower.builder()
                .followerUserId(followerUserId)
                .followeeUserId(followeeUserId)
                .build();
        followerRepository.save(follower);
    }

    @Override
    public void unfollow(String followerUserId, String followeeUserId) throws Exception {
        Follower follower = followerRepository.findByFollowerUserIdAndFolloweeUserId(followerUserId, followeeUserId);
        if (follower == null) {
            throw new Exception("You are not following this user");
        }
        followerRepository.delete(follower);
    }
}
