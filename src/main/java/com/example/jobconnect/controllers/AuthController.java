package com.example.jobconnect.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.jobconnect.model.User;
import com.example.jobconnect.service.UserService;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String homePage() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerSubmit(@ModelAttribute User user, Model model) {
        if (userService.findByEmail(user.getEmail()).isPresent()) {
            model.addAttribute("error", "Email already in use");
            return "register";
        }

        if (user.getRole() == null || user.getRole().isBlank()) {
            user.setRole("JOB_SEEKER");
        }

        userService.saveUser(user);
        return "redirect:/login";
    }

    @GetMapping("/profile")
    public String profile(Authentication auth, Model model) {
        if (auth == null) return "redirect:/login";
        model.addAttribute("email", auth.getName());
        return "profile";
    }
}