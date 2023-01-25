package com.planner.server.domain.study_goal.dto;

import com.planner.server.domain.study_goal.entity.StudyGoal;
import lombok.Builder;
import lombok.Data;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class StudyGoalDto {

    private UUID id;
    private String title;
    private String detail;

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Duration goalTime;
    private String userProfileName;

    public static StudyGoalDto toDto(StudyGoal studyGoal){
        return StudyGoalDto.builder()
                .id(studyGoal.getId())
                .title(studyGoal.getGoalTitle())
                .detail(studyGoal.getGoalDetail())
                .startDate(studyGoal.getStartDate())
                .endDate(studyGoal.getEndDate())
                .goalTime(studyGoal.getGoalTime())
                .userProfileName(studyGoal.getUser().getProfileName())
                .build();
    }
}
