package com.api.twitter.service;

import com.api.twitter.model.response.NotificationResponse;

import java.util.List;

public interface NotificationService {
    void addNotification(String username, String actionType, String postId) throws Exception;

    List<NotificationResponse> getAllNotifications(String userId) throws Exception;
}
