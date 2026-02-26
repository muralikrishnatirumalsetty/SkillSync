package com.demo.skillsync.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import com.demo.skillsync.user.service.UserService;
import com.demo.skillsync.user.entity.User;
import com.demo.skillsync.user.dto.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final UserService userService;

    @GetMapping("/users")
    public List<UserResponse> getAllUsers() {
        return userService.getAll()
                .stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    @GetMapping("/users/{id}")
    public UserResponse getUser(@PathVariable UUID id) {
        return map(userService.getById(id));
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable UUID id) {
        userService.delete(id);
    }

    @PutMapping("/users/{id}/role")
    public void updateRole(@PathVariable UUID id,
                           @RequestBody UpdateRoleRequest request) {
        userService.updateRole(id, request.getRole());
    }

    @PutMapping("/users/{id}/status")
    public void updateStatus(@PathVariable UUID id,
                             @RequestBody UpdateStatusRequest request) {
        userService.updateStatus(id, request.getStatus());
    }

    private UserResponse map(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .status(user.getAccountStatus().name())
                .build();
    }
}
