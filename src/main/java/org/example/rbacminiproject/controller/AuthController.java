package org.example.rbacminiproject.controller;
import jakarta.validation.Valid;

import org.example.rbacminiproject.dto.UserSignUpRequest;
import org.example.rbacminiproject.exception.DuplicateEmailException;
import org.example.rbacminiproject.service.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public String signup(@Valid @ModelAttribute("userSignupRequest") UserSignUpRequest request, BindingResult bindingResult) {

        if (bindingResult.hasErrors()){
            return "signup";
        }

        try {
            authService.registerUser(request);
        } catch (DuplicateEmailException e) {

            bindingResult.rejectValue("email", "email.exists", e.getMessage());

            return "signup";
        }

        authService.registerUser(request);

        return "redirect:/login";
    }
    
}
