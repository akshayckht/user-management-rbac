package org.example.rbacminiproject.service.impl;

import org.example.rbacminiproject.dto.AdminUserCreateRequest;
import org.example.rbacminiproject.dto.AdminUserUpdateRequest;
import org.example.rbacminiproject.entity.Role;
import org.example.rbacminiproject.entity.User;
import org.example.rbacminiproject.exception.DuplicateEmailException;
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

    public AdminServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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
    public User createUser(AdminUserCreateRequest request) {

        if (userRepository.existsByEmail(request.email())) {
            throw new DuplicateEmailException("Email id already registered");
        }

        User user = new User();

        user.setName(request.name());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));

        if (Objects.equals(request.role(), Role.USER.name())) {
            user.setRole(Role.USER);
        } else {
            user.setRole(Role.ADMIN);
        }

        return userRepository.save(user);
    }



    @Override
    @Transactional
    public void updateUser(Long id, AdminUserUpdateRequest request) {

        User user = getUserById(id);

        if (!user.getEmail().equalsIgnoreCase(request.email())
                && userRepository.existsByEmail(request.email())) {

            throw new DuplicateEmailException("Email id already registered");
        }

        user.setName(request.name());
        user.setEmail(request.email());
        user.setRole(request.role());

        userRepository.save(user);
    }



    @Override
    @Transactional
    public void deleteUser(Long id) {

        User user = getUserById(id);
        userRepository.delete(user);
    }
}
