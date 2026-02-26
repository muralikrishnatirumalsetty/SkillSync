package com.demo.skillsync.skill.service;

import com.demo.skillsync.skill.spec.SkillSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.demo.skillsync.skill.repository.SkillRepository;
import com.demo.skillsync.skill.entity.*;
import com.demo.skillsync.skill.dto.*;
import com.demo.skillsync.user.repository.UserRepository;
import com.demo.skillsync.user.entity.User;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SkillService {

    private final SkillRepository skillRepository;
    private final UserRepository userRepository;

    public SkillResponse createSkill(String email, CreateSkillRequest request) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Skill skill = Skill.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .category(request.getCategory())
                .level(SkillLevel.valueOf(request.getLevel()))
                .user(user)
                .build();

        return map(skillRepository.save(skill));
    }

    public List<SkillResponse> getMySkills(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return skillRepository.findByUserId(user.getId())
                .stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    public SkillResponse getSkill(UUID id) {
        return map(skillRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Skill not found")));
    }

    public SkillResponse updateSkill(UUID id, String email, UpdateSkillRequest request) {

        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Skill not found"));

        if (!skill.getUser().getEmail().equals(email)) {
            throw new RuntimeException("Unauthorized");
        }

        skill.setTitle(request.getTitle());
        skill.setDescription(request.getDescription());
        skill.setCategory(request.getCategory());
        skill.setLevel(SkillLevel.valueOf(request.getLevel()));

        return map(skillRepository.save(skill));
    }

    public void deleteSkill(UUID id, String email) {

        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Skill not found"));

        if (!skill.getUser().getEmail().equals(email)) {
            throw new RuntimeException("Unauthorized");
        }

        skillRepository.delete(skill);
    }

    public List<SkillResponse> getAllSkills() {
        return skillRepository.findAll()
                .stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    private SkillResponse map(Skill skill) {
        return SkillResponse.builder()
                .id(skill.getId())
                .title(skill.getTitle())
                .description(skill.getDescription())
                .category(skill.getCategory())
                .level(skill.getLevel().name())
                .createdAt(skill.getCreatedAt())
                .build();
    }

    public Page<SkillResponse> getAllSkillsPaginated(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        return skillRepository.findAll(pageable)
                .map(this::map);
    }

    public Page<SkillResponse> searchSkills(
            String keyword,
            String category,
            String level,
            Pageable pageable
    ) {

        Specification<Skill> spec =
                Specification.where(SkillSpecification.search(keyword))
                        .and(SkillSpecification.filterByCategory(category))
                        .and(SkillSpecification.filterByLevel(level));

        return skillRepository.findAll(spec, pageable)
                .map(this::map);
    }
}