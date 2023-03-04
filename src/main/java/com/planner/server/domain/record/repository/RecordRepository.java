package com.planner.server.domain.record.repository;

import com.planner.server.domain.record.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RecordRepository extends JpaRepository<Record, Long> {

    @Query("select r from Record r join fetch r.user join fetch r.userStudySubject where r.id = :id")
    Optional<Record> findByIdJoinFetchUserAndUserStudySubject(@Param("id")UUID id);

    @Query("select r from Record r join fetch r.user join fetch r.userStudySubject where r.user.id = :userId")
    List<Record> findByUserJoinFetchUserAndUserStudySubject(@Param("userId") UUID userId);

    @Query("select r from Record r join fetch r.userStudySubject join fetch r.user where r.userStudySubject.id = :userStudySubjectId")
    List<Record> findByUserStudySubjectJoinFetchUserAndUserStudySubject(@Param("userStudySubjectId") UUID userStudySubjectId);

}
