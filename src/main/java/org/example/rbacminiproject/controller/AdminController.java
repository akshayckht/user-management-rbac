package org.example.rbacminiproject.controller;

import jakarta.validation.Valid;

import org.example.rbacminiproject.dto.AdminUserCreateRequest;
import org.example.rbacminiproject.dto.AdminUserUpdateRequest;
import org.example.rbacminiproject.entity.User;
import org.example.rbacminiproject.exception.DuplicateEmailException;
import org.example.rbacminiproject.service.AdminService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        List<User> users = adminService.getAllUsers();
        model.addAttribute("users", users);

        return "admin/dashboard";
    }

    @GetMapping("/user")
    public String searchUser(@RequestParam String keyword, Model model) {

        List<User> users = adminService.searchUser(keyword);

        model.addAttribute("users", users);

        return "admin/dashboard";
    }

    @GetMapping("/createuser")
    public String getUserForm(Model model) {
        model.addAttribute("adminUserCreateRequest", new AdminUserCreateRequest(null, null, null, null));
        return "admin/createUser";
    }

    @PostMapping("/createuser/user")
    public String createUser(
            @Valid @ModelAttribute("adminUserCreateRequest") AdminUserCreateRequest request,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "admin/createUser";
        }

        try {
            adminService.createUser(request);
        } catch (DuplicateEmailException e) {

            bindingResult.rejectValue("email", "email.exists", e.getMessage());

            return "admin/createUser";
        }

        return "redirect:/admin/dashboard";
    }

    @PostMapping("/user/{id}/delete")
    public String deleteUser(@PathVariable Long id){

        adminService.deleteUser(id);

        return "redirect:/admin/dashboard";
    }

    @GetMapping("/user/{id}/edit")
    public String getEditForm(@PathVariable Long id, Model model) {

        User user = adminService.getUserById(id);

        AdminUserUpdateRequest adminUserUpdateRequest =
                new AdminUserUpdateRequest(user.getName(), user.getEmail(), user.getRole().name());

        model.addAttribute("adminUserUpdateRequest", adminUserUpdateRequest);
        model.addAttribute("userid", id);

        return "admin/editUser";
    }

    @PostMapping("/user/{id}/update")
    public String updateUser(
            @PathVariable Long id,
            @Valid @ModelAttribute("adminUserUpdateRequest") AdminUserUpdateRequest request,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "admin/userUpdateFailed";
        }

        try {
            adminService.updateUser(id, request);
        } catch (DuplicateEmailException e) {

            bindingResult.rejectValue("email", "email.exists", e.getMessage());
            return "admin/userUpdateFailed";
        }

        return "redirect:/admin/dashboard";
    }
}
