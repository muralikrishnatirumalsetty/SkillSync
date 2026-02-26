package com.demo.skillsync.skill.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Sort;

import com.demo.skillsync.skill.service.SkillService;
import com.demo.skillsync.skill.dto.SkillResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PublicSkillController {

    private final SkillService skillService;

    @GetMapping("/skills")
    public Page<SkillResponse> getSkills(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String level,
            @PageableDefault(size = 6, sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        return skillService.searchSkills(keyword, category, level, pageable);
    }
}
