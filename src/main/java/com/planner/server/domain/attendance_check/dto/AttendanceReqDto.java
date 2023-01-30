package com.planner.server.domain.attendance_check.dto;

import com.planner.server.domain.user.dto.UserResDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class AttendanceReqDto {

    private UUID id;
    private LocalDateTime createdAt;
    private UUID userId;
}
