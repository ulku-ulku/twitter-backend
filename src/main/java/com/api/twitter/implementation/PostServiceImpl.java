package com.api.twitter.implementation;

import com.api.twitter.entity.Post;
import com.api.twitter.entity.User;
import com.api.twitter.model.response.PostResponse;
import com.api.twitter.repository.CommentRepository;
import com.api.twitter.repository.LikeRepository;
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

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private CommentRepository commentRepository;

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
    public List<PostResponse> getAllPosts(User loggedInUser) throws Exception {
        List<Post> posts = postRepository.findAll();
        List<PostResponse> postResponseList = new ArrayList<>();

        for (Post post : posts) {
            User user = userRepository.getById(post.getUserId());
            PostResponse postResponse = setPostResponse(post, user, loggedInUser);
            postResponseList.add(postResponse);
        }

        return postResponseList;
    }

    @Override
    public List<PostResponse> getPostsByUserId(String userId, User loggedInUser) throws Exception {
        List<Post> posts = postRepository.findByUserId(userId);
        List<PostResponse> responses = new ArrayList<>();

        for (Post post : posts) {
            User user = userRepository.getById(post.getUserId());
            PostResponse response = setPostResponse(post, user, loggedInUser);
            responses.add(response);
        }

        return responses;
    }

    @Override
    public PostResponse getById(String postId, User loggedInUser) throws Exception {
        Post post = postRepository.getById(postId);
        User user = userRepository.getById(post.getUserId());
        PostResponse response = setPostResponse(post, user, loggedInUser);
        return response;
    }

    private PostResponse setPostResponse(Post post, User user, User loggedInUser) throws Exception {
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
                .hasLiked(likeRepository.existsByPostIdAndUserId(post.getId(), loggedInUser.getId()))
                .likeCount(likeRepository.countByPostId(post.getId()))
                .commentCount(commentRepository.countByPostId(post.getId()))
                .build();
    }
}
