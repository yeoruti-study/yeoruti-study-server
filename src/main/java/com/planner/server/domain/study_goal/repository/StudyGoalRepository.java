package com.planner.server.domain.study_goal.repository;

import com.planner.server.domain.study_goal.entity.StudyGoal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StudyGoalRepository extends JpaRepository<StudyGoal, Long> {

    @Query("select s from StudyGoal s join fetch s.user where s.user.id = :userId")
    List<StudyGoal> findByUserJoinFetchUser(@Param("userId") UUID userId);

    @Query("select s from StudyGoal s join fetch s.user where s.user.id = :userId and s.goalTitle = :goalTitle")
    Optional<StudyGoal> findByUserAndTitleJoinFetchUser(@Param("userId") UUID userId, @Param("goalTitle") String title);

    @Query("select distinct s from StudyGoal s join fetch s.user where s.id = :id")
    Optional<StudyGoal> findByIdJoinFetchUser(@Param("id") UUID id);

    @Query("select s from StudyGoal s where s.userStudySubjectId = :id")
    List<StudyGoal> findByUserStudySubject(@Param("id") UUID id);

}
