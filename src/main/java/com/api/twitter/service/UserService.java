package com.api.twitter.service;

import com.api.twitter.model.request.SignUpRequest;

public interface UserService {
    void register(SignUpRequest signUpRequest) throws Exception;
}
