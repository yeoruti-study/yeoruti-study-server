package com.planner.server.domain.room_user.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.planner.server.domain.room_user.entity.RoomUser;
import com.planner.server.domain.study_room.entity.StudyRoom;
import com.planner.server.domain.user.entity.User;

@Repository
public interface RoomUserRepository extends JpaRepository<RoomUser, Long> {
    Optional<RoomUser> findById(UUID id);
    List<RoomUser> findByUser(User user);
    List<RoomUser> findByStudyRoom(StudyRoom studyRoom);
}
