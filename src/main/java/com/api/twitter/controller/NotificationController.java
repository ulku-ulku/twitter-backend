package com.api.twitter.controller;

import com.api.twitter.entity.User;
import com.api.twitter.model.response.NotificationResponse;
import com.api.twitter.service.NotificationService;
import com.api.twitter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserService userService;

    @GetMapping()
    public ResponseEntity<List<NotificationResponse>> getNotifications(
            @RequestHeader("Authorization") String userToken
    ) throws Exception {
        try {
            User loggedInUser = userService.getUserByTokenId(userToken);
            List<NotificationResponse> responses = notificationService.getAllNotifications(loggedInUser.getId());
            return ResponseEntity.ok(responses);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
