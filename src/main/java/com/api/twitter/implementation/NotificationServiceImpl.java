package com.api.twitter.implementation;

import com.api.twitter.entity.Notification;
import com.api.twitter.entity.Post;
import com.api.twitter.model.response.NotificationResponse;
import com.api.twitter.model.response.PostResponse;
import com.api.twitter.repository.NotificationRepository;
import com.api.twitter.repository.PostRepository;
import com.api.twitter.service.NotificationService;
import com.api.twitter.service.PostService;
import io.netty.util.concurrent.ImmediateEventExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private PostRepository postRepository;

    @Override
    public void addNotification(String username, String actionType, String postId) throws Exception {
        Post post = postRepository.getById(postId);
        String message = username.concat(" has ").concat(actionType).concat(" your post");

        Notification notification = Notification.builder()
                .userId(post.getUserId())
                .postId(postId)
                .message(message)
                .build();
        notificationRepository.save(notification);
    }

    @Override
    public List<NotificationResponse> getAllNotifications(String userId) throws Exception {
        List<Notification> notifications = notificationRepository.findByUserId(userId);
        List<NotificationResponse> responses = new ArrayList<>();

        for (Notification notification : notifications) {
            NotificationResponse response = NotificationResponse.builder()
                    .id(notification.getId())
                    .message(notification.getMessage())
                    .postId(notification.getPostId())
                    .build();
            responses.add(response);
        }

        return responses;
    }

}
