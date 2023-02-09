package com.planner.server.domain.study_goal.dto;

import com.planner.server.domain.study_goal.entity.StudyGoal;
import com.planner.server.domain.user.dto.UserResDto;
import lombok.Builder;
import lombok.Getter;

import java.time.Duration;
import java.time.LocalDateTime;
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
    private UserResDto userDto;

    public static StudyGoalResDto toDto(StudyGoal studyGoal){
        return StudyGoalResDto.builder()
                .id(studyGoal.getId())
                .title(studyGoal.getGoalTitle())
                .detail(studyGoal.getGoalDetail())
                .startDate(studyGoal.getStartDate())
                .endDate(studyGoal.getEndDate())
                .goalTime(studyGoal.getGoalTime())
                .userDto(UserResDto.toDto(studyGoal.getUser()))
                .build();
    }
}
