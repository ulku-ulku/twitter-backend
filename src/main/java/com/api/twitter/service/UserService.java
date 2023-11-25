package com.api.twitter.service;

import com.api.twitter.model.request.SignUpRequest;
import com.api.twitter.model.response.UserDetailResponse;

public interface UserService {
    void register(SignUpRequest signUpRequest) throws Exception;

    UserDetailResponse getUserByTokenId(String userToken) throws Exception;
}
