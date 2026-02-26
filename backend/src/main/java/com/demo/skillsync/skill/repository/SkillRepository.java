package com.demo.skillsync.skill.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.demo.skillsync.skill.entity.Skill;

import java.util.List;
import java.util.UUID;

public interface SkillRepository
        extends JpaRepository<Skill, UUID>,
        JpaSpecificationExecutor<Skill> {

    List<Skill> findByUserId(UUID userId);
}
