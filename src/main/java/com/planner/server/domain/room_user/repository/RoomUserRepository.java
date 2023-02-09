package com.planner.server.domain.room_user.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.planner.server.domain.room_user.entity.RoomUser;

@Repository
public interface RoomUserRepository extends JpaRepository<RoomUser, Long> {
    Optional<RoomUser> findById(UUID id);

    @Query("SELECT DISTINCT ru FROM RoomUser ru JOIN FETCH ru.user WHERE ru.user.id = :userId")
    List<RoomUser> findByUser(@Param("userId") UUID userId);

    @Query("SELECT DISTINCT ru FROM RoomUser ru JOIN FETCH ru.user WHERE ru.studyRoom.id = :studyRoomId")
    List<RoomUser> findByStudyRoom(@Param("studyRoomId") UUID studyRoomId);
}
