package org.example.rbacminiproject.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import org.example.rbacminiproject.entity.Role;

public record AdminUserCreateRequest(

        @NotBlank(message = "Name Required") String name,

        @Email(message = "Enter a Valid Email") @NotBlank(message = "Email required") String email,

        @NotBlank(message = "Password required")
        @Size(min = 8, message = "Password must be at least 8 characters")
        String password,

        @NotNull(message = "Role is required")
        Role role
) {}
