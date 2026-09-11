package org.example.rbacminiproject.service;

import org.example.rbacminiproject.dto.UserSignUpRequest;


public interface UserService {

    void registerUser(UserSignUpRequest request);

}
