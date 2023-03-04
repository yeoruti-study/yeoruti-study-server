package com.planner.server.domain.study_goal.dto;

import com.planner.server.domain.study_goal.entity.StudyGoal;
import com.planner.server.domain.user.dto.UserResDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
public class StudyGoalResDto {

    private UUID id;
    private String title;
    private String detail;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Duration goalTime;

    public static StudyGoalResDto toDto(StudyGoal studyGoal){
        return StudyGoalResDto.builder()
                .id(studyGoal.getId())
                .title(studyGoal.getGoalTitle())
                .detail(studyGoal.getGoalDetail())
                .startDate(studyGoal.getStartDate())
                .endDate(studyGoal.getEndDate())
                .goalTime(studyGoal.getGoalTime())
                .build();
    }
}
