package org.example.rbacminiproject.mapper;

import org.example.rbacminiproject.dto.AdminUserCreateRequest;
import org.example.rbacminiproject.dto.AdminUserUpdateRequest;
import org.example.rbacminiproject.dto.UserSignUpRequest;
import org.example.rbacminiproject.entity.Role;
import org.example.rbacminiproject.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User mapToEntity(UserSignUpRequest signUpRequest){

        User user = new User();

        user.setName(signUpRequest.name());
        user.setEmail(signUpRequest.email());
        user.setRole(Role.USER);

        return user;
    }

    public User mapToEntity(AdminUserCreateRequest userCreateRequest){

        User user = new User();

        user.setName(userCreateRequest.name());
        user.setEmail(userCreateRequest.email());
        user.setRole(userCreateRequest.role());

        return user;
    }

    public User updateEntity(AdminUserUpdateRequest userUpdateRequest, User user){

        user.setName(userUpdateRequest.name());
        user.setEmail(userUpdateRequest.email());
        user.setRole(userUpdateRequest.role());

        return user;
    }
}
