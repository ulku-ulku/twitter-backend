package com.api.twitter.service;

import com.api.twitter.entity.User;
import com.api.twitter.model.request.SignUpRequest;
import com.api.twitter.model.response.UserDetailResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {
    void register(SignUpRequest signUpRequest) throws Exception;

    UserDetailResponse convertToUserResponse(User user) throws Exception;

    User getUserByTokenId(String userToken) throws Exception;

    UserDetailResponse getUserById(String id, User loggedInUser) throws Exception;

    void updateProfile(User currentUser, MultipartFile profilePicture, MultipartFile bannerPicture, String bio) throws Exception;

    List<UserDetailResponse> getAllUsers() throws Exception;
}
