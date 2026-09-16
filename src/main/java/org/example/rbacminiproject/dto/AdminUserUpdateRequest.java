package org.example.rbacminiproject.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.example.rbacminiproject.entity.Role;

public record AdminUserUpdateRequest(

        @NotBlank(message = "Name Required")
        String name,

        @Email(message = "Enter a Valid Email")
        @NotBlank(message = "Email required")
        String email,

        @NotBlank(message = "Role is required")
        Role role
) {}
