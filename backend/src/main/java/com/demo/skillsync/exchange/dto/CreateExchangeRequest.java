package com.demo.skillsync.exchange.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateExchangeRequest {

    @NotNull
    private UUID skillId;
}
