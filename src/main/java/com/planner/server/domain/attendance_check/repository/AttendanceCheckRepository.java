package com.planner.server.domain.attendance_check.repository;

import com.planner.server.domain.attendance_check.entity.AttendanceCheck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AttendanceCheckRepository extends JpaRepository<AttendanceCheck, Long> {

    Optional<AttendanceCheck> findById(UUID id);

    @Query("SELECT DISTINCT ac from AttendanceCheck ac JOIN FETCH ac.user u WHERE u.id = :userId")
    List<AttendanceCheck> findByUserId(@Param("userId") UUID userId);

    @Query("SELECT ac from AttendanceCheck ac WHERE ac.createdAt BETWEEN :startDate AND :endDate")
    Optional<AttendanceCheck> findByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
