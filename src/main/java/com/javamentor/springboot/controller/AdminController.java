package com.javamentor.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.javamentor.springboot.model.Role;
import com.javamentor.springboot.model.User;
import com.javamentor.springboot.service.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    // form 'admin_panel'
    @GetMapping("/crud_user")
    public String GetUsers() {
        return "admin/crud_user";
    }

    // Add new User
    @PostMapping("/add_user")
    public String AddUser(@ModelAttribute("User") User user
            ,@RequestParam(value = "ROLE_ADMIN", required = false) String role_admin
            ,@RequestParam(value = "ROLE_USER", required = false) String role_user) {
        if((user.getName() != null) & (user.getPassword() != null)) {
            Set<Role> roles = new HashSet<>();
            if(role_admin != null) {
                roles.add(userService.getRole("ROLE_ADMIN"));
            }
            if(role_user != null) {
                roles.add(userService.getRole("ROLE_USER"));
            }
            if(!roles.isEmpty()){
                user.setRoles(roles);
                userService.addUser(user);
            }
        }
        return "redirect:/admin/crud_user";
    }

    // Update User
    @GetMapping("/upd_user/{id}")
    public String EditUsers(Model model, @PathVariable("id") Long id) {
        model.addAttribute("EditingUser", userService.getUser(id));
        return "admin/upd_user";
    }

    @PostMapping("/upd_current_user")
    public String UpdateUser(@ModelAttribute("EditingUser") User user
            ,@RequestParam(value = "ROLE_ADMIN", required = false) String role_admin
            ,@RequestParam(value = "ROLE_USER", required = false) String role_user
            ,@RequestParam(value = "oldPassword", required = false) String oldPassword) {
        if((user.getName() != null) & (user.getPassword() != null)) {
            Set<Role> roles = new HashSet<>();
            if(role_admin != null) {
                roles.add(userService.getRole("ROLE_ADMIN"));
            }
            if(role_user != null) {
                roles.add(userService.getRole("ROLE_USER"));
            }
            if(!roles.isEmpty()){
                user.setRoles(roles);
                if(user.getPassword().equals(oldPassword)) {
                    userService.updUser(user, false);
                } else {
                    userService.updUser(user, true);
                }
            }
        }
        return "redirect:/admin/crud_user";
    }

    // Delete User
    @PostMapping("/del_user/{id}")
    public String DeleteUser(@PathVariable("id") Long id) {
        userService.delUser(id);
        return "redirect:/admin/crud_user";
    }

    @ModelAttribute("Users")
    public List<User> ListUsers() {
        return userService.getUsers();
    }

    @ModelAttribute("User")
    public User getNewUser() {
        return new User();
    }
}
