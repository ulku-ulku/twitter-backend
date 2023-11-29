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
@Table(name = Follower.TABLE_NAME)
public class Follower {
    public static final String TABLE_NAME = "followers";
    private static final String ID = "id";
    private static final String FOLLOWER_USER_ID = "follower_user_id";
    private static final String FOLLOWEE_USER_ID = "followee_user_id";

    @Id
    @Column(name = Follower.ID)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @org.springframework.data.annotation.Id
    private String id;

    @Column(name = Follower.FOLLOWER_USER_ID)
    private String followerUserId;

    @Column(name = Follower.FOLLOWEE_USER_ID)
    private String followeeUserId;
}
