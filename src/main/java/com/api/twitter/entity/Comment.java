package com.api.twitter.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = Comment.TABLE_NAME)
public class Comment {
    public static final String TABLE_NAME = "comments";
    private static final String ID = "id";
    private static final String USER_ID = "user_id";
    private static final String POST_ID = "post_id";
    private static final String COMMENT = "comment";
    private static final String CREATED_AT = "created_at";

    @Id
    @Column(name = Comment.ID)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @org.springframework.data.annotation.Id
    private String id;

    @Column(name = Comment.USER_ID)
    private String userId;

    @Column(name = Comment.POST_ID)
    private String postId;

    @Column(name = Comment.COMMENT)
    private String comment;

    @Column(name = Comment.CREATED_AT)
    private Date createdAt;
}
