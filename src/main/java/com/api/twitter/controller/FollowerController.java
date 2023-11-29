package com.api.twitter.controller;

import com.api.twitter.entity.User;
import com.api.twitter.service.FollowerService;
import com.api.twitter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/follow")
public class FollowerController {
    @Autowired
    private FollowerService followerService;

    @Autowired
    private UserService userService;

    @PostMapping("/{follower_user_id}")
    public ResponseEntity<String> follow(
            @RequestHeader("Authorization") String userToken,
            @PathVariable("follower_user_id") String followerUserId
    ) throws Exception {
        try {
            User currentUser = userService.getUserByTokenId(userToken);
            followerService.follow(followerUserId, currentUser.getId());
            return ResponseEntity.ok("Follow successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{follower_user_id}")
    public ResponseEntity<String> unfollow(
            @RequestHeader("Authorization") String userToken,
            @PathVariable("follower_user_id") String followerUserId
    ) throws Exception {
        try {
            User currentUser = userService.getUserByTokenId(userToken);
            followerService.unfollow(followerUserId, currentUser.getId());
            return ResponseEntity.ok("Unfollow successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
