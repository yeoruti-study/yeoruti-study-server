package com.planner.server.domain.study_goal.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class SaveReqDto {

    private String title;
    private String detail;

    private String goalTime;
    private String startDate;
    private String endDate;
    private String userId;
}
