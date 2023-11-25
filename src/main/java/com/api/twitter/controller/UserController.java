package com.api.twitter.controller;

import com.api.twitter.model.request.SignUpRequest;
import com.api.twitter.model.response.UserDetailResponse;
import com.api.twitter.service.UserService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            UserDetailResponse response = userService.getUserByTokenId(userToken);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
