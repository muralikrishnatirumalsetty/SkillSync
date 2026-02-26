package com.demo.skillsync.exchange.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.demo.skillsync.exchange.entity.ExchangeRequest;

import java.util.List;
import java.util.UUID;

public interface ExchangeRepository extends JpaRepository<ExchangeRequest, UUID> {

    List<ExchangeRequest> findByRequesterId(UUID requesterId);

    List<ExchangeRequest> findBySkillUserId(UUID userId);
}