package com.api.twitter.controller;

import com.api.twitter.entity.User;
import com.api.twitter.model.request.SignUpRequest;
import com.api.twitter.model.response.UserDetailResponse;
import com.api.twitter.service.UserService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestBody SignUpRequest signUpRequest
    ) throws Exception {
        try {
            userService.register(signUpRequest);
            return ResponseEntity.ok("User registered successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/detail/token")
    public ResponseEntity<UserDetailResponse> getUserDetailByToken(
            @RequestHeader("Authorization") String userToken
    ) throws Exception {
        try {
            User user = userService.getUserByTokenId(userToken);
            UserDetailResponse response = userService.convertToUserResponse(user);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/detail/{user_id}")
    public ResponseEntity<UserDetailResponse> getUserDetail(
            @RequestHeader("Authorization") String userToken,
            @PathVariable("user_id") String userId
    ) throws Exception {
        try {
            UserDetailResponse response = userService.getUserById(userId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping()
    public ResponseEntity<String> updateProfile(
        @RequestPart(name = "profilePicture", required = false) MultipartFile profilePicture,
        @RequestPart(name = "bannerPicture", required = false) MultipartFile bannerPicture,
        @RequestParam String bio,
        @RequestHeader("Authorization") String userToken
    ) throws Exception {
        try {
            User currentUser = userService.getUserByTokenId(userToken);
            userService.updateProfile(currentUser, profilePicture, bannerPicture, bio);
            return ResponseEntity.ok("User updated successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
