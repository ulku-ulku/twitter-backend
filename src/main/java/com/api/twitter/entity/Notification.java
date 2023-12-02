package com.api.twitter.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = Notification.TABLE_NAME)
public class Notification {
    public static final String TABLE_NAME = "notifications";
    private static final String ID = "id";
    private static final String USER_ID = "user_id";
    private static final String POST_ID = "post_id";
    private static final String MESSAGE = "message";

    @Id
    @Column(name = Notification.ID)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @org.springframework.data.annotation.Id
    private String id;

    @Column(name = Notification.USER_ID)
    private String userId;

    @Column(name = Notification.POST_ID)
    private String postId;

    @Column(name = Notification.MESSAGE)
    private String message;
}
