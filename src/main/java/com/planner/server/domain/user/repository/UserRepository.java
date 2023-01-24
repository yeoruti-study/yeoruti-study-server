package com.planner.server.domain.user.repository;

import com.planner.server.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByProfileName(String profileName);

    Optional<User> findById(UUID id);

    Optional<User> findByUsername(String username);
}