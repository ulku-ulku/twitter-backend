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
@Table(name = User.TABLE_NAME)
public class User {
    public static final String TABLE_NAME = "users";
    private static final String ID = "id";
    private static final String USERNAME = "username";
    private static final String EMAIL = "email";
    private static final String FIREBASE_UID = "firebase_uid";
    private static final String REGISTERED_DATE = "registered_date";

    @Id
    @Column(name = User.ID)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @org.springframework.data.annotation.Id
    private String id;

    @Column(name = User.USERNAME)
    private String username;

    @Column(name = User.EMAIL)
    private String email;

    @Column(name = User.FIREBASE_UID)
    private String firebaseUid;

    @Column(name = User.REGISTERED_DATE)
    private Date registeredDate;
}
