package com.planner.server.domain.refresh_token.repository;

import com.planner.server.domain.refresh_token.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    @Query("select r from RefreshToken r where r.id = :id")
    Optional<RefreshToken> findById(@Param("id") UUID id);

    @Query("select r from RefreshToken r where r.userId = :userId")
    Optional<RefreshToken> findByUserId(UUID userId);

}