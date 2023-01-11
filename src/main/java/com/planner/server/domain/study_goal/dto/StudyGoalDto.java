package com.planner.server.domain.study_goal.dto;

import com.planner.server.domain.study_goal.entity.StudyGoal;
import lombok.Builder;
import lombok.Data;

import java.time.Duration;
import java.time.LocalDateTime;

@Data
@Builder
public class StudyGoalDto {

    private String title;
    private String detail;

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Duration goalTime;

    private String userProfileName;

    public static StudyGoalDto toDto(StudyGoal studyGoal, String userProfileName){
        return StudyGoalDto.builder()
                .title(studyGoal.getGoalTitle())
                .detail(studyGoal.getGoalDetail())
                .startDate(studyGoal.getStartDate())
                .endDate(studyGoal.getEndDate())
                .goalTime(studyGoal.getGoalTime())
                .userProfileName(userProfileName)
                .build();
    }
}
