package com.demo.skillsync.exchange.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class ExchangeResponse {

    private UUID id;
    private String requesterName;
    private String skillTitle;
    private String skillOwner;
    private String status;
    private LocalDateTime createdAt;
}