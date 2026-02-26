package com.demo.skillsync.exchange.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.demo.skillsync.exchange.repository.ExchangeRepository;
import com.demo.skillsync.exchange.entity.*;
import com.demo.skillsync.exchange.dto.*;
import com.demo.skillsync.user.repository.UserRepository;
import com.demo.skillsync.skill.repository.SkillRepository;
import com.demo.skillsync.user.entity.User;
import com.demo.skillsync.skill.entity.Skill;
import com.demo.skillsync.common.exception.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExchangeService {

    private final ExchangeRepository exchangeRepository;
    private final UserRepository userRepository;
    private final SkillRepository skillRepository;

    public ExchangeResponse createRequest(String email, UUID skillId) {

        User requester = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new ResourceNotFoundException("Skill not found"));

        if (skill.getUser().getId().equals(requester.getId())) {
            throw new BadRequestException("You cannot request your own skill");
        }

        ExchangeRequest request = ExchangeRequest.builder()
                .requester(requester)
                .skill(skill)
                .status(ExchangeStatus.PENDING)
                .build();

        return map(exchangeRepository.save(request));
    }

    public ExchangeResponse updateStatus(UUID requestId, String email, ExchangeStatus status) {

        ExchangeRequest request = exchangeRepository.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Request not found"));

        if (!request.getSkill().getUser().getEmail().equals(email)) {
            throw new UnauthorizedException("Only skill owner can update status");
        }

        if (request.getStatus() != ExchangeStatus.PENDING) {
            throw new BadRequestException("Request already processed");
        }

        request.setStatus(status);

        return map(exchangeRepository.save(request));
    }

    public void cancelRequest(UUID requestId, String email) {

        ExchangeRequest request = exchangeRepository.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Request not found"));

        if (!request.getRequester().getEmail().equals(email)) {
            throw new UnauthorizedException("Only requester can cancel");
        }

        if (request.getStatus() != ExchangeStatus.PENDING) {
            throw new BadRequestException("Cannot cancel processed request");
        }

        request.setStatus(ExchangeStatus.CANCELLED);
        exchangeRepository.save(request);
    }

    private ExchangeResponse map(ExchangeRequest request) {
        return ExchangeResponse.builder()
                .id(request.getId())
                .requesterName(request.getRequester().getName())
                .skillTitle(request.getSkill().getTitle())
                .skillOwner(request.getSkill().getUser().getName())
                .status(request.getStatus().name())
                .createdAt(request.getCreatedAt())
                .build();
    }

    public List<ExchangeResponse> getSentRequests(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return exchangeRepository.findByRequesterId(user.getId())
                .stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    public List<ExchangeResponse> getIncomingRequests(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return exchangeRepository.findBySkillUserId(user.getId())
                .stream()
                .map(this::map)
                .collect(Collectors.toList());
    }
}