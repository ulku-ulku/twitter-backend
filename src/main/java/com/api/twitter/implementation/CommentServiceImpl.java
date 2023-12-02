package com.api.twitter.implementation;

import com.api.twitter.entity.Comment;
import com.api.twitter.entity.User;
import com.api.twitter.model.response.CommentResponse;
import com.api.twitter.repository.CommentRepository;
import com.api.twitter.repository.UserRepository;
import com.api.twitter.service.CommentService;
import com.api.twitter.service.UserService;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void addComment(String comment, String postId, String userId) throws Exception {
        Comment c = Comment.builder()
                .postId(postId)
                .userId(userId)
                .createdAt(new Date())
                .comment(comment)
                .build();
        commentRepository.save(c);
    }

    @Override
    public List<CommentResponse> getCommentsByPostId(String postId) throws Exception {
        List<Comment> commentList = commentRepository.findByPostId(postId);
        List<CommentResponse> commentResponseList = new ArrayList<>();

        for (Comment comment : commentList) {
            User user = userRepository.getById(comment.getUserId());
            byte[] profileImageByte = null;
            if (StringUtils.isNotBlank(user.getProfilePicturePath())) {
                profileImageByte = Files.readAllBytes(Paths.get(user.getProfilePicturePath()));
            }

            CommentResponse commentResponse = CommentResponse.builder()
                    .id(comment.getId())
                    .comment(comment.getComment())
                    .postId(comment.getPostId())
                    .userId(comment.getUserId())
                    .username(user.getUsername())
                    .createdAt(comment.getCreatedAt())
                    .profileImage(profileImageByte)
                    .build();
            commentResponseList.add(commentResponse);
        }

        return commentResponseList;
    }
}
