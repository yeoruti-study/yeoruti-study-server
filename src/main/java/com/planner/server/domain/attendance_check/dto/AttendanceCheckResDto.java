package com.planner.server.domain.attendance_check.dto;

import com.planner.server.domain.attendance_check.entity.AttendanceCheck;
import com.planner.server.domain.user.dto.UserResDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class AttendanceCheckResDto {
    private UUID id;
    private UserResDto userResDto;
    private LocalDateTime createdAt;

    public static AttendanceCheckResDto toDto(AttendanceCheck attendance){
        UserResDto userDto = UserResDto.toDto(attendance.getUser());

        return AttendanceCheckResDto.builder()
                .id(attendance.getId())
                .userResDto(userDto)
                .createdAt(attendance.getCreatedAt())
                .build();
    }
}
