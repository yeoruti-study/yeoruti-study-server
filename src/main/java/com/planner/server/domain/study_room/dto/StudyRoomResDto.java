package com.planner.server.domain.study_room.dto;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

import com.planner.server.domain.study_category.dto.StudyCategoryResDto;
import com.planner.server.domain.study_category.entity.StudyCategory;
import com.planner.server.domain.study_room.entity.StudyRoom;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class StudyRoomResDto {
    private UUID id;
    private String name;
    private StudyCategoryResDto studyCategoryDto;   // study room을 조회할 때, 카테고리는 항상 포함
    private int maximumNumberOfPeople;
    private Duration studyGoalTime;
    private UUID masterUserId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean hasRoomPassword;

    public static StudyRoomResDto toDto(StudyRoom entity) {
        StudyCategory studyCategory = entity.getStudyCategory();
        StudyCategoryResDto studyCategoryDto = StudyCategoryResDto.builder()
            .id(studyCategory.getId())
            .name(studyCategory.getName())
            .description(studyCategory.getDescription())
            .build();

        return StudyRoomResDto.builder()
            .id(entity.getId())
            .name(entity.getName())
            .studyCategoryDto(studyCategoryDto)
            .maximumNumberOfPeople(entity.getMaximumNumberOfPeople())
            .studyGoalTime(entity.getStudyGoalTime())
            .masterUserId(entity.getMasterUserId())
            .createdAt(entity.getCreatedAt())
            .updatedAt(entity.getUpdatedAt())
            .hasRoomPassword(entity.isHasRoomPassword())
            .build();
    }

    @Getter
    @Builder
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class IncludesMasterUserInfo {
        private UUID id;
        private String name;
        private StudyCategoryResDto studyCategoryDto;   // study room을 조회할 때, 카테고리는 항상 포함
        private int maximumNumberOfPeople;
        private Duration studyGoalTime;
        private UUID masterUserId;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private String masterUserUsername;
        private String masterUserProfileName;
        private boolean hasRoomPassword;
    }
}
