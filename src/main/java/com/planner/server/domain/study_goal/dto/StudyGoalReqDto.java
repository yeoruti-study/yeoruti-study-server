package com.planner.server.domain.study_goal.dto;

import com.planner.server.domain.study_goal.entity.StudyGoal;
import lombok.Builder;
import lombok.Data;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class StudyGoalReqDto {

    private UUID id;
    private String title;
    private String detail;

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Duration goalTime;
    private UUID userStudySubjectId;

    public static StudyGoalReqDto toDto(StudyGoal studyGoal){
        return StudyGoalReqDto.builder()
                .id(studyGoal.getId())
                .title(studyGoal.getGoalTitle())
                .detail(studyGoal.getGoalDetail())
                .startDate(studyGoal.getStartDate())
                .endDate(studyGoal.getEndDate())
                .goalTime(studyGoal.getGoalTime())
                .userStudySubjectId(studyGoal.getUserStudySubjectId())
                .build();
    }
}
