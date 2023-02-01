package com.planner.server.domain.study_category.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.planner.server.domain.study_category.entity.StudyCategory;

@Repository
public interface StudyCategoryRepository extends JpaRepository<StudyCategory, Long> {
    Optional<StudyCategory> findById(UUID id);
}
