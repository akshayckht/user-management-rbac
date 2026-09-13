package org.example.rbacminiproject.service;

import org.example.rbacminiproject.dto.UserSignUpRequest;

public interface AuthService {

    void registerUser(UserSignUpRequest request);
    

}
