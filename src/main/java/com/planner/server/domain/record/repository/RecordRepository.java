package com.planner.server.domain.record.repository;

import com.planner.server.domain.record.entity.Record;
import com.planner.server.domain.user_study_subject.entity.UserStudySubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RecordRepository extends JpaRepository<Record, Long> {

    @Query("select r from Record r join fetch r.user join fetch r.userStudySubject where r.id = :id")
    Optional<Record> findById(@Param("id")UUID id);

//    @Query("select distinct r from Record r join fetch r.user")
//    List<Record> findAllByFetchJoin();

    @Query("select r from Record r join fetch r.user join fetch r.userStudySubject where r.user.id = :userId")
    List<Record> findByUserId(@Param("userId") UUID userId);

    @Query("select r from Record r join fetch r.userStudySubject join fetch r.user where r.userStudySubject.id = :userStudySubjectId")
    List<Record> findByUserStudySubjectId(@Param("userStudySubjectId") UUID userStudySubjectId);

}
