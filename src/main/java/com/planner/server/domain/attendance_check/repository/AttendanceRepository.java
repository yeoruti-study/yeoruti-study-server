package com.planner.server.domain.attendance_check.repository;

import com.planner.server.domain.attendance_check.entity.AttendanceCheck;
import com.planner.server.domain.friend.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AttendanceRepository extends JpaRepository<AttendanceCheck, Long> {

    Optional<AttendanceCheck> findById(UUID id);

    @Query("select distinct a from AttendanceCheck a join fetch a.user where a.id = :id")
    Optional<AttendanceCheck> findByIdJoinFetchUser(UUID id);

    List<AttendanceCheck> findByUserId(@Param("id")UUID id);


}
