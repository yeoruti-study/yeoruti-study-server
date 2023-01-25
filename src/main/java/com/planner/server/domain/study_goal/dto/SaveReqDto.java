package com.planner.server.domain.study_goal.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class SaveReqDto {

    private String title;
    private String detail;

    private String goalTime;
    private String startDate;
    private String endDate;
    private UUID userId;
}
