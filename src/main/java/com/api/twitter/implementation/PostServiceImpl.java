package com.api.twitter.implementation;

import com.api.twitter.entity.Post;
import com.api.twitter.entity.User;
import com.api.twitter.model.response.PostResponse;
import com.api.twitter.repository.PostRepository;
import com.api.twitter.repository.UserRepository;
import com.api.twitter.service.PostService;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void post(String content, String userId) throws Exception {
        Post post = Post.builder()
                .content(content)
                .userId(userId)
                .createdAt(new Date())
                .build();
        postRepository.save(post);
    }

    @Override
    public List<PostResponse> getAllPosts() throws Exception {
        List<Post> posts = postRepository.findAll();
        List<PostResponse> postResponseList = new ArrayList<>();

        for (Post post : posts) {
            User user = userRepository.getById(post.getUserId());
            PostResponse postResponse = setPostResponse(post, user);
            postResponseList.add(postResponse);
        }

        return postResponseList;
    }

    private PostResponse setPostResponse(Post post, User user) throws Exception {
        byte[] profileImageByte = null;
        if (StringUtils.isNotBlank(user.getProfilePicturePath())) {
            profileImageByte = Files.readAllBytes(Paths.get(user.getProfilePicturePath()));
        }

        return PostResponse.builder()
                .id(post.getId())
                .createdAt(post.getCreatedAt())
                .content(post.getContent())
                .userId(post.getUserId())
                .username(user.getUsername())
                .profilePicture(profileImageByte)
                .build();
    }
}
