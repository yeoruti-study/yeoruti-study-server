package com.planner.server.domain.refresh_token.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "refresh_token")
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RefreshToken {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long cid;

    @Type(type = "uuid-char")
    private UUID id;

    private String refreshToken;

    private LocalDateTime createdAt;
    
    @Type(type = "uuid-char")
    private UUID userId;
}
