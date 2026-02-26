package com.demo.skillsync.skill.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SkillResponse {

    private UUID id;
    private String title;
    private String description;
    private String category;
    private String level;
    private LocalDateTime createdAt;
}
