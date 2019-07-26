package com.vkncode.evento.controller;

import com.vkncode.evento.exception.ResourceNotFoundException;
import com.vkncode.evento.model.User;
import com.vkncode.evento.repository.UserRepository;
import com.vkncode.evento.security.UserPrincipal;
import com.vkncode.evento.security.CurrentUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public UserPrincipal getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        return currentUser;
    }

    @GetMapping("/user/checkUsernameAvailability")
    public boolean checkUsernameAvailability(@RequestParam(value = "username") String username) {
        return !userRepository.existsByUsername(username);
    }

    @GetMapping("/user/checkEmailAvailability")
    public boolean checkEmailAvailability(@RequestParam(value = "email") String email) {
        return !userRepository.existsByEmail(email);
    }

    @GetMapping("/users/{username}")
    public User getUserProfile(@PathVariable(value = "username") String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        return user;
    }

}