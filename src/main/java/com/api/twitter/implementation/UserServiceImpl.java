package com.api.twitter.implementation;

import com.api.twitter.entity.User;
import com.api.twitter.model.request.SignUpRequest;
import com.api.twitter.repository.UserRepository;
import com.api.twitter.service.UserService;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.FirebaseAuth;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public void register(SignUpRequest signUpRequest) throws Exception {
        try {
            UserRecord.CreateRequest createRequest = new UserRecord.CreateRequest()
                    .setEmail(signUpRequest.getEmail())
                    .setPassword(signUpRequest.getPassword());
            UserRecord firebaseResponse = FirebaseAuth
                    .getInstance()
                    .createUser(createRequest);
            if (firebaseResponse == null && StringUtils.isBlank(firebaseResponse.getUid())) {
                throw new Exception("User registration failed due to invalid response");
            }

            User user = User.builder()
                    .username(signUpRequest.getUsername())
                    .email(signUpRequest.getEmail())
                    .registeredDate(new Date())
                    .firebaseUid(firebaseResponse.getUid())
                    .build();

            userRepository.save(user);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
