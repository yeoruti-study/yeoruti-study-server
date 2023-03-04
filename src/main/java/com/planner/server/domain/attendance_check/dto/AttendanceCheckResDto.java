package com.planner.server.domain.attendance_check.dto;

import com.planner.server.domain.attendance_check.entity.AttendanceCheck;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class AttendanceCheckResDto {
    private UUID id;
    private LocalDateTime createdAt;

    public static AttendanceCheckResDto toDto(AttendanceCheck attendance){

        return AttendanceCheckResDto.builder()
                .id(attendance.getId())
                .createdAt(attendance.getCreatedAt())
                .build();
    }
}
