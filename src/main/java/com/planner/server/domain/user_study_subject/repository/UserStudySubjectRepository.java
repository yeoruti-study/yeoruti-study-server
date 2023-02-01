package com.planner.server.domain.user_study_subject.repository;

import com.planner.server.domain.user_study_subject.entity.UserStudySubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserStudySubjectRepository extends JpaRepository<UserStudySubject, Long> {

    @Query("select distinct s from UserStudySubject s join fetch s.user where s.user.id =:id")
    List<UserStudySubject> findByUserId(@Param("id") UUID id);

    Optional<UserStudySubject> findByTitle(String title);

    @Query("select s from UserStudySubject s where s.user.id = :userId and s.title = :title")
    Optional<UserStudySubject> duplicateCheck(@Param("userId") UUID userId,
                                              @Param("title") String title);

    Optional<UserStudySubject> findById(UUID id);

    @Query("select distinct s from UserStudySubject s join fetch s.user  where s.user.id =:id")
    Optional<UserStudySubject> findByIdFetchJoin(UUID id);
}
