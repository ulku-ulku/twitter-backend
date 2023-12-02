package com.api.twitter.repository;

import com.api.twitter.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, String> {
    Post getById(String postId);

    List<Post> findByUserId(String userId);
}
