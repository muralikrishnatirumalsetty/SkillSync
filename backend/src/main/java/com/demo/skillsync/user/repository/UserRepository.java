package com.demo.skillsync.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.demo.skillsync.user.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
}
