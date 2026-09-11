package org.example.rbacminiproject.controller;

import org.example.rbacminiproject.dto.UserSignUpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/signup")
    public String signUp(Model model) {

        model.addAttribute("userSignupRequest", new UserSignUpRequest(null, null, null));
        return "signup";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
