package com.demo.skillsync.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import com.demo.skillsync.user.service.UserService;
import com.demo.skillsync.user.entity.User;
import com.demo.skillsync.user.dto.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private String extractEmail(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return user.getEmail();
    }

    @GetMapping("/profile")
    public UserResponse getProfile(Authentication authentication) {

        String email = extractEmail(authentication);
        User user = userService.getByEmail(email);

        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .status(user.getAccountStatus().name())
                .build();
    }

    @PutMapping("/profile")
    public void updateProfile(Authentication authentication,
                              @RequestBody UpdateProfileRequest request) {
        String email = extractEmail(authentication);
        userService.updateProfile(email, request);
    }

    @PutMapping("/change-password")
    public void changePassword(Authentication authentication,
                               @RequestBody ChangePasswordRequest request) {
        String email = extractEmail(authentication);
        userService.changePassword(email, request);
    }

    @DeleteMapping("/delete")
    public void deleteOwnAccount(Authentication authentication) {
        String email = extractEmail(authentication);
        User user = userService.getByEmail(email);
        userService.delete(user.getId());
    }
}