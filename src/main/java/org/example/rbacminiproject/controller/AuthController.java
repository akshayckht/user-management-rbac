package org.example.rbacminiproject.controller;

import jakarta.validation.Valid;

import org.example.rbacminiproject.dto.UserSignUpRequest;
import org.example.rbacminiproject.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public String signup(@Valid @ModelAttribute("userSignupRequest") UserSignUpRequest request, BindingResult bindingResult) {

        if (bindingResult.hasErrors()){
            return "signup";
        }
            userService.registerUser(request);

        return "redirect:/login";
    }
}
