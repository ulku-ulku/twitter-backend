package com.api.twitter.implementation;

import com.api.twitter.entity.Like;
import com.api.twitter.repository.LikeRepository;
import com.api.twitter.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeServiceImpl implements LikeService {
    @Autowired
    private LikeRepository likeRepository;

    @Override
    public void like(String postId, String userId) {
        Like like = Like.builder()
                .postId(postId)
                .userId(userId)
                .build();

        likeRepository.save(like);
    }

    @Override
    public void removeLike(String postId, String userId) {
        Like like = likeRepository.findByPostIdAndUserId(postId, userId);
        likeRepository.delete(like);
    }
}
