package com.planner.server.domain.attendance_check.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceCheckReqDto {
    private UUID id;
    private UUID userId;
}
