package com.demo.skillsync.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.demo.skillsync.user.repository.UserRepository;
import com.demo.skillsync.user.entity.*;
import com.demo.skillsync.user.dto.*;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User getByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User getById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public void delete(UUID id) {
        userRepository.deleteById(id);
    }

    public void updateRole(UUID id, String role) {
        User user = getById(id);
        user.setRole(Role.valueOf(role));
        userRepository.save(user);
    }

    public void updateStatus(UUID id, String status) {
        User user = getById(id);
        user.setAccountStatus(AccountStatus.valueOf(status));
        userRepository.save(user);
    }

    public void updateProfile(String email, UpdateProfileRequest request) {
        User user = getByEmail(email);
        user.setName(request.getName());
        userRepository.save(user);
    }

    public void changePassword(String email, ChangePasswordRequest request) {
        User user = getByEmail(email);

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new RuntimeException("Old password incorrect");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }
}
