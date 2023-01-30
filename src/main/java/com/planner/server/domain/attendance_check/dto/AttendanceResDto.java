package com.planner.server.domain.attendance_check.dto;

import com.planner.server.domain.attendance_check.entity.AttendanceCheck;
import com.planner.server.domain.user.dto.UserResDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class AttendanceResDto {

    private UUID id;
    private LocalDateTime createdAt;
    private UserResDto userResDto;
    public static AttendanceResDto toDto(AttendanceCheck attendance){
        return AttendanceResDto.builder()
                .id(attendance.getId())
                .createdAt(attendance.getCreatedAt())
                .userResDto(UserResDto.toDto(attendance.getUser()))
                .build();
    }
}
