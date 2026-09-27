package org.example.rbacminiproject.service.impl;

import org.example.rbacminiproject.dto.UserSignUpRequest;
import org.example.rbacminiproject.entity.Role;
import org.example.rbacminiproject.entity.User;
import org.example.rbacminiproject.exception.DuplicateEmailException;
import org.example.rbacminiproject.mapper.UserMapper;
import org.example.rbacminiproject.repository.UserRepository;
import org.example.rbacminiproject.service.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public AuthServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }


    @Override
    public void registerUser(UserSignUpRequest request) throws DuplicateEmailException{

        if (userRepository.existsByEmail(request.email())) {
            throw new DuplicateEmailException("Email id already registered");
        }

        User user = userMapper.mapToEntity(request);
        user.setPassword(passwordEncoder.encode(request.password()));

        userRepository.save(user);
    }

}
