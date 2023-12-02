package com.api.twitter.controller;

import com.api.twitter.entity.User;
import com.api.twitter.model.request.PostRequest;
import com.api.twitter.model.response.PostResponse;
import com.api.twitter.service.PostService;
import com.api.twitter.service.UserService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {
    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @PostMapping()
    public ResponseEntity<String> post(
            @RequestHeader("Authorization") String userToken,
            @RequestBody PostRequest postRequest
    ) throws Exception {
        try {
            User user = userService.getUserByTokenId(userToken);
            postService.post(postRequest.getContent(), user.getId());
            return ResponseEntity.ok("Tweet created successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping()
    public ResponseEntity<List<PostResponse>> getAllPosts(
            @RequestHeader("Authorization") String userToken
    ) throws Exception {
        try {
            User loggedInUser = userService.getUserByTokenId(userToken);
            List<PostResponse> postResponseList = postService.getAllPosts(loggedInUser);
            return ResponseEntity.ok(postResponseList);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
