package org.example.rbacminiproject.service.impl;

import org.example.rbacminiproject.dto.AdminUserCreateRequest;
import org.example.rbacminiproject.dto.AdminUserUpdateRequest;
import org.example.rbacminiproject.entity.Role;
import org.example.rbacminiproject.entity.User;
import org.example.rbacminiproject.exception.DuplicateEmailException;
import org.example.rbacminiproject.mapper.UserMapper;
import org.example.rbacminiproject.repository.UserRepository;
import org.example.rbacminiproject.service.AdminService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public AdminServiceImpl(UserRepository userRepository,
                            PasswordEncoder passwordEncoder,
                            UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }



    @Override
    @Transactional(readOnly = true)
    public List<User> searchUser(String keyword) {

        if (keyword == null || keyword.isBlank()) {
            return userRepository.findAll();
        }

        String searchTerm = keyword.trim();

        return userRepository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
                searchTerm, searchTerm);
    }


    @Override
    public User getUserById(Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    @Override
    @Transactional
    public void createUser(AdminUserCreateRequest userCreateRequest) throws DuplicateEmailException {

        if (userRepository.existsByEmail(userCreateRequest.email())) {
            throw new DuplicateEmailException("Email id already registered");
        }

        User user = userMapper.mapToEntity(userCreateRequest);
        user.setPassword(passwordEncoder.encode(userCreateRequest.password()));

        userRepository.save(user);
    }



    @Override
    @Transactional
    public void updateUser(Long id, AdminUserUpdateRequest updateRequest) throws DuplicateEmailException {

        User user = getUserById(id);

        if (!user.getEmail().equalsIgnoreCase(updateRequest.email())
                && userRepository.existsByEmail(updateRequest.email())) {

            throw new DuplicateEmailException("Email id already registered");
        }

       user = userMapper.updateEntity(updateRequest,user);

        userRepository.save(user);
    }



    @Override
    @Transactional
    public void deleteUser(Long id) {

        User user = getUserById(id);
        userRepository.delete(user);
    }
}
