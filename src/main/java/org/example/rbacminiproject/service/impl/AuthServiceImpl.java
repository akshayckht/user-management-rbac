package org.example.rbacminiproject.service.impl;

import org.example.rbacminiproject.dto.UserSignUpRequest;
import org.example.rbacminiproject.entity.Role;
import org.example.rbacminiproject.entity.User;
import org.example.rbacminiproject.exception.DuplicateEmailException;
import org.example.rbacminiproject.repository.UserRepository;
import org.example.rbacminiproject.service.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void registerUser(UserSignUpRequest request) throws DuplicateEmailException{

        if (userRepository.existsByEmail(request.email())) {
            throw new DuplicateEmailException("Email id already registered");
        }

        userRepository.save(mapToUser(request));
    }

    // mapping logic
    private User mapToUser(UserSignUpRequest request) {
        User user = new User();

        user.setName(request.name());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(Role.USER);
        return user;
    }

}
