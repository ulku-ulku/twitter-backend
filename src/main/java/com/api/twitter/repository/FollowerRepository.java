package com.api.twitter.repository;

import com.api.twitter.entity.Follower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowerRepository extends JpaRepository<Follower, String> {
    Follower findByFollowerUserIdAndFolloweeUserId(String followerUserId, String followeeUserId);

    boolean existsByFollowerUserIdAndFolloweeUserId(String followerUserId, String followeeUserId);

    long countByFollowerUserId(String userId);

    long countByFolloweeUserId(String userId);
}
