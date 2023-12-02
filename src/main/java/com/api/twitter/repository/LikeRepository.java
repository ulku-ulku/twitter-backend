package com.api.twitter.repository;

import com.api.twitter.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, String> {
    Like findByPostIdAndUserId(String postId, String userId);

    Long countByPostId(String postId);

    boolean existsByPostIdAndUserId(String postId, String userId);
}
