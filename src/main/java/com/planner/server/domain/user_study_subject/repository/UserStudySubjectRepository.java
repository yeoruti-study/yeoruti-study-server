package com.planner.server.domain.user_study_subject.repository;

import com.planner.server.domain.user_study_subject.entity.UserStudySubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserStudySubjectRepository extends JpaRepository<UserStudySubject, Long> {

    @Query("select us from UserStudySubject us join fetch us.user where us.user.id = :id")
    List<UserStudySubject> findByUserJoinFetchUser(@Param("id") UUID id);

    @Query("select us from UserStudySubject us join fetch us.user where us.user.id = :userId and us.title = :title")
    Optional<UserStudySubject> findByUserAndTitleJoinFetchUser(@Param("userId") UUID userId,
                                              @Param("title") String title);

    @Query("select us from UserStudySubject us join fetch us.user where us.id = :id")
    Optional<UserStudySubject> findByIdJoinFetchUser(@Param("id") UUID id);

//    @Query("select distinct s from UserStudySubject s join fetch s.user where s.id =:id")
//    Optional<UserStudySubject> findByIdFetchJoin(UUID id);
}
