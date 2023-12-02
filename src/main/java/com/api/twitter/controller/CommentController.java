package com.api.twitter.controller;

import com.api.twitter.entity.User;
import com.api.twitter.model.request.CommentRequest;
import com.api.twitter.model.response.CommentResponse;
import com.api.twitter.service.CommentService;
import com.api.twitter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @PostMapping("/{post_id}")
    public ResponseEntity<String> addComment(
            @PathVariable("post_id") String postId,
            @RequestBody CommentRequest commentRequest,
            @RequestHeader("Authorization") String userToken
    ) throws Exception {
        try {
            User loggedInUser = userService.getUserByTokenId(userToken);
            commentService.addComment(commentRequest.getComment(), postId, loggedInUser.getId());
            return ResponseEntity.ok("Comment added successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{post_id}")
    public ResponseEntity<List<CommentResponse>> getComments(
        @PathVariable("post_id") String postId
    ) {
        try {
            List<CommentResponse> commentResponseList = commentService.getCommentsByPostId(postId);
            return ResponseEntity.ok(commentResponseList);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
