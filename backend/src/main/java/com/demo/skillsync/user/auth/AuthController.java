package com.demo.skillsync.user.auth;

import com.demo.skillsync.common.exception.ResourceNotFoundException;
import com.demo.skillsync.common.exception.UnauthorizedException;
import com.demo.skillsync.common.response.ApiResponse;
import com.demo.skillsync.user.entity.AccountStatus;
import com.demo.skillsync.user.entity.Role;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.demo.skillsync.user.repository.UserRepository;
import com.demo.skillsync.user.entity.User;
import com.demo.skillsync.user.dto.*;
import com.demo.skillsync.security.jwt.JwtService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ApiResponse<AuthResponse> login(@Valid @RequestBody LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (user.getAccountStatus().name().equals("BLOCKED")) {
            throw new UnauthorizedException("Account is blocked");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("Invalid credentials");
        }

        String token = jwtService.generateToken(
                user.getEmail(),
                user.getRole().name()
        );

        AuthResponse response = AuthResponse.builder()
                .token(token)
                .build();

        return ApiResponse.success("Login successful", response);
    }

    @PostMapping("/register")
    public ApiResponse<String> register(@Valid @RequestBody RegisterRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER) // default role
                .accountStatus(AccountStatus.ACTIVE) // default status
                .build();

        userRepository.save(user);

        return ApiResponse.success("User registered successfully", null);
    }

}