package org.example.rbacminiproject.service;

import org.example.rbacminiproject.dto.AdminUserCreateRequest;
import org.example.rbacminiproject.dto.AdminUserUpdateRequest;
import org.example.rbacminiproject.entity.User;


import java.util.List;

public interface AdminService {

    List<User> getAllUsers();

    List<User> searchUser(String keyword);

    User getUserById(Long id);

    void createUser(AdminUserCreateRequest request);

    void updateUser(Long id, AdminUserUpdateRequest request);

    void deleteUser(Long id);
}
