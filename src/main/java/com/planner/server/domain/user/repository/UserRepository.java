package com.planner.server.domain.user.repository;

import com.planner.server.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where u.id = :id")
    Optional<User> findById(@Param("id") UUID id);

    @Query("select distinct u from User u join fetch u.userStudySubjects where u.id = :id")
    Optional<User> findByIdJoinFetchUserStudySubject(@Param("id") UUID id);

    Optional<User> findByUsername(String username);

}
