package org.example.rbacminiproject.controller;

import jakarta.servlet.http.HttpSession;

import org.example.rbacminiproject.dto.LoginRequest;
import org.example.rbacminiproject.dto.UserSignUpRequest;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
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
    public String login(Model model, Authentication authentication) {

        if (authentication != null
                && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken)) {

            return "redirect:/dashboard";
        }

        model.addAttribute("loginRequest", new LoginRequest(null, null));

        return "login";
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {

        return "dashboardUser";
    }


}
