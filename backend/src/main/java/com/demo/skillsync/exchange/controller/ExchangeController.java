package com.demo.skillsync.exchange.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import com.demo.skillsync.exchange.service.ExchangeService;
import com.demo.skillsync.exchange.dto.ExchangeResponse;
import com.demo.skillsync.exchange.entity.ExchangeStatus;
import com.demo.skillsync.common.response.ApiResponse;
import com.demo.skillsync.user.entity.User;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/exchange")
public class ExchangeController {

    private final ExchangeService exchangeService;

    @PostMapping("/{skillId}")
    public ApiResponse<ExchangeResponse> createRequest(
            @PathVariable UUID skillId,
            Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();

        return ApiResponse.success(
                "Request created",
                exchangeService.createRequest(user.getEmail(), skillId)
        );
    }

    @GetMapping("/sent")
    public ApiResponse<List<ExchangeResponse>> getSent(
            Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();

        return ApiResponse.success(
                "Sent requests fetched",
                exchangeService.getSentRequests(user.getEmail())
        );
    }

    @GetMapping("/incoming")
    public ApiResponse<List<ExchangeResponse>> getIncoming(
            Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();

        return ApiResponse.success(
                "Incoming requests fetched",
                exchangeService.getIncomingRequests(user.getEmail())
        );
    }

    @PutMapping("/{id}/accept")
    public ApiResponse<ExchangeResponse> accept(
            @PathVariable UUID id,
            Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();

        return ApiResponse.success(
                "Request accepted",
                exchangeService.updateStatus(
                        id,
                        user.getEmail(),
                        ExchangeStatus.ACCEPTED
                )
        );
    }

    @PutMapping("/{id}/reject")
    public ApiResponse<ExchangeResponse> reject(
            @PathVariable UUID id,
            Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();

        return ApiResponse.success(
                "Request rejected",
                exchangeService.updateStatus(
                        id,
                        user.getEmail(),
                        ExchangeStatus.REJECTED
                )
        );
    }

    @PutMapping("/{id}/cancel")
    public ApiResponse<Object> cancel(
            @PathVariable UUID id,
            Authentication authentication
    ) {
        String email = authentication.getName();

        exchangeService.cancelRequest(id, email);

        return ApiResponse.success("Request cancelled", null);
    }
}