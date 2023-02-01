package com.planner.server.domain.study_room.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.planner.server.domain.study_room.entity.StudyRoom;

@Repository
public interface StudyRoomRepository extends JpaRepository<StudyRoom, Long> {
    Optional<StudyRoom> findById(UUID id);
    
    @Query("SELECT DISTINCT sr FROM StudyRoom sr JOIN FETCH sr.studyCategory")
    List<StudyRoom> findAllAndRelatedStudyCategory();

    @Query("SELECT DISTINCT sr FROM StudyRoom sr JOIN FETCH sr.studyCategory sc WHERE sc.id = :studyCategoryId")
    List<StudyRoom> findListByStudyCategoryId(@Param("studyCategoryId") UUID studyCategoryId);
}
