package org.example.rbacminiproject.exception;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, Model model){


        return "error";
    }


    @ExceptionHandler(UsernameNotFoundException.class)
    public String handleUserNameNotFoundException(UsernameNotFoundException e, Model model) {

        model.addAttribute("exception", e.getName());

        return "login";
    }

}
