package com.api.twitter.controller;

import com.api.twitter.entity.User;
import com.api.twitter.service.LikeService;
import com.api.twitter.service.UserService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/like")
public class LikeController {
    @Autowired
    private LikeService likeService;

    @Autowired
    private UserService userService;

    @PostMapping("/{post_id}")
    public ResponseEntity<String> like (
        @RequestHeader("Authorization") String userToken,
        @PathVariable("post_id") String postId
    ) throws Exception {
        try {
            User loggedInUser = userService.getUserByTokenId(userToken);
            likeService.like(postId, loggedInUser.getId());
            return ResponseEntity.ok("Like added successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{post_id}")
    public ResponseEntity<String> removeLike(
        @RequestHeader("Authorization") String userToken,
        @PathVariable("post_id") String postId
    ) throws Exception {
        try {
            User loggedInUser = userService.getUserByTokenId(userToken);
            likeService.removeLike(postId, loggedInUser.getId());
            return ResponseEntity.ok("Like removed successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
