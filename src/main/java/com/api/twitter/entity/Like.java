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
@Table(name = Like.TABLE_NAME)
public class Like {
    public static final String TABLE_NAME = "likes";
    private static final String ID = "id";
    private static final String USER_ID = "user_id";
    private static final String POST_ID = "post_id";

    @Id
    @Column(name = Like.ID)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @org.springframework.data.annotation.Id
    private String id;

    @Column(name = Like.USER_ID)
    private String userId;

    @Column(name = Like.POST_ID)
    private String postId;
}
