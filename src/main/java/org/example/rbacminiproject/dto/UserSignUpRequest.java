package org.example.rbacminiproject.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

public record UserSignUpRequest(

        @NotBlank(message = "Name is required")
        String name,

        @Email
        @NotBlank(message = "Email is required")
        String email,

        @NotBlank(message = "Password is required")
        @Size(min = 8, message = " Password Must be at least 8 characters" )
        String password

) implements Serializable {}
