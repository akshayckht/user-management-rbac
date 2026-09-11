package org.example.rbacminiproject.service.impl;

import org.example.rbacminiproject.dto.UserSignUpRequest;
import org.example.rbacminiproject.entity.Role;
import org.example.rbacminiproject.entity.User;
import org.example.rbacminiproject.exception.DuplicateEmailException;
import org.example.rbacminiproject.repository.UserRepository;
import org.example.rbacminiproject.service.UserService;
import org.springframework.stereotype.Service;



@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void registerUser(UserSignUpRequest request) {

        if (userRepository.existsByEmail(request.email())) {
            throw new DuplicateEmailException("Email id already registered");
        }

        userRepository.save(mapToUser(request));
    }

    private User mapToUser(UserSignUpRequest request) {
        User user = new User();

        user.setName(request.name());
        user.setEmail(request.email());
        user.setPassword(request.password());
        user.setRole(Role.USER);
        return user;
    }
}
