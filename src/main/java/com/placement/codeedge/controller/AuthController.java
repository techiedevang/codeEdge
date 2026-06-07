package com.placement.codeedge.controller;

import com.placement.codeedge.model.User;
import com.placement.codeedge.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String loginPage(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/";
        }
        return "login";
    }

    @GetMapping("/signup")
    public String signupPage(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/";
        }
        return "signup";
    }

    @PostMapping("/signup")
    public String processSignup(@RequestParam String username,
                                @RequestParam String email,
                                @RequestParam String password,
                                Model model) {
        if (userRepository.findByUsername(username).isPresent()) {
            model.addAttribute("error", "Username already exists!");
            return "signup";
        }
        if (userRepository.findByEmail(email).isPresent()) {
            model.addAttribute("error", "Email is already registered!");
            return "signup";
        }

        User user = User.builder()
                .username(username.trim())
                .email(email.trim())
                .password(passwordEncoder.encode(password))
                .solvedProblems(new ArrayList<>())
                .build();

        userRepository.save(user);
        return "redirect:/login?registered=true";
    }
}
