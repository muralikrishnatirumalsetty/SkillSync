package com.demo.skillsync.skill.spec;

import com.demo.skillsync.skill.entity.Skill;
import com.demo.skillsync.skill.entity.SkillLevel;

import org.springframework.data.jpa.domain.Specification;

public class SkillSpecification {

    public static Specification<Skill> search(String keyword) {
        return (root, query, cb) -> {
            if (keyword == null || keyword.isEmpty()) return null;

            String pattern = "%" + keyword.toLowerCase() + "%";

            return cb.or(
                    cb.like(cb.lower(root.get("title")), pattern),
                    cb.like(cb.lower(root.get("description")), pattern),
                    cb.like(cb.lower(root.get("category")), pattern)
            );
        };
    }

    public static Specification<Skill> filterByCategory(String category) {
        return (root, query, cb) -> {
            if (category == null || category.isEmpty()) return null;
            return cb.equal(root.get("category"), category);
        };
    }

    public static Specification<Skill> filterByLevel(String level) {
        return (root, query, cb) -> {
            if (level == null || level.isEmpty()) return null;
            return cb.equal(root.get("level"), SkillLevel.valueOf(level));
        };
    }
}
