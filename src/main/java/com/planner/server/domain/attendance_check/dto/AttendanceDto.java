package com.planner.server.domain.attendance_check.dto;

import com.planner.server.domain.attendance_check.entity.AttendanceCheck;
import com.planner.server.domain.user.dto.UserDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class AttendanceDto {

    private UUID id;
    private UserDto userDto;
    private LocalDateTime createdAt;

    public static AttendanceDto toDto(AttendanceCheck attendanceCheck, UserDto userDto){
        return AttendanceDto.builder()
                .id(attendanceCheck.getId())
                .userDto(userDto)
                .createdAt(attendanceCheck.getCreatedAt())
                .build();
    }
}
