package com.demo.skillsync.user.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private UUID id;
    private String name;
    private String email;
    private String role;
    private String status;
}
