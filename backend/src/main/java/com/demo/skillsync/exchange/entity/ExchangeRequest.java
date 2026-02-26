package com.demo.skillsync.exchange.entity;

import jakarta.persistence.*;
import lombok.*;
import com.demo.skillsync.user.entity.User;
import com.demo.skillsync.skill.entity.Skill;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "exchange_requests")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRequest {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requester_id")
    private User requester;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skill_id")
    private Skill skill;

    @Enumerated(EnumType.STRING)
    private ExchangeStatus status;

    private LocalDateTime createdAt;

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
    }
}