package com.demo.skillsync.skill.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import jakarta.validation.Valid;

import com.demo.skillsync.skill.service.SkillService;
import com.demo.skillsync.skill.dto.*;
import com.demo.skillsync.user.entity.User;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class SkillController {

    private final SkillService skillService;

    // ===================== USER APIs =====================

    @PostMapping("/api/user/skills")
    public SkillResponse createSkill(Authentication authentication,
                                     @Valid @RequestBody CreateSkillRequest request) {

        User user = (User) authentication.getPrincipal();
        return skillService.createSkill(user.getEmail(), request);
    }

    @GetMapping("/api/user/skills")
    public List<SkillResponse> getMySkills(Authentication authentication) {

        User user = (User) authentication.getPrincipal();
        return skillService.getMySkills(user.getEmail());
    }

    @GetMapping("/api/user/skills/{id}")
    public SkillResponse getSkill(@PathVariable UUID id) {
        return skillService.getSkill(id);
    }

    @PutMapping("/api/user/skills/{id}")
    public SkillResponse updateSkill(@PathVariable UUID id,
                                     Authentication authentication,
                                     @Valid @RequestBody UpdateSkillRequest request) {

        User user = (User) authentication.getPrincipal();
        return skillService.updateSkill(id, user.getEmail(), request);
    }

    @DeleteMapping("/api/user/skills/{id}")
    public void deleteSkill(@PathVariable UUID id,
                            Authentication authentication) {

        User user = (User) authentication.getPrincipal();
        skillService.deleteSkill(id, user.getEmail());
    }

    // ===================== ADMIN API =====================

    @GetMapping("/api/admin/skills")
    public List<SkillResponse> getAllSkills() {
        return skillService.getAllSkills();
    }
}
