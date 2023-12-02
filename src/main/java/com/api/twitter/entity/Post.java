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
@Table(name = Post.TABLE_NAME)
public class Post {
    public static final String TABLE_NAME = "posts";
    private static final String ID = "id";
    private static final String USER_ID = "user_id";
    private static final String CONTENT = "content";
    private static final String CREATED_AT = "created_at";

    @Id
    @Column(name = Post.ID)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @org.springframework.data.annotation.Id
    private String id;

    @Column(name = Post.USER_ID)
    private String userId;

    @Column(name = Post.CONTENT)
    private String content;

    @Column(name = Post.CREATED_AT)
    private Date createdAt;
}
