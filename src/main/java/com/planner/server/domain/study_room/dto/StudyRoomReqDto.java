package com.planner.server.domain.study_room.dto;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

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
public class StudyRoomReqDto {
    private UUID id;
    private String name;
    private int maximumNumberOfPeople;
    private Duration studyGoalTime;
    private String roomPassword;
    private UUID masterUserId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean hasRoomPassword;

    @Getter
    @Builder
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class JoinStudyCategory {
        private UUID id;
        private String name;
        private UUID studyCategoryId;
        private int maximumNumberOfPeople;
        private Duration studyGoalTime;
        private String roomPassword;
        private UUID masterUserId;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private boolean hasRoomPassword;
    }

    @Getter
    @Builder
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReqChangePassword {
        private UUID id;
        private String roomPassword;
    }

    @Getter
    @Builder
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReqCheckRoomPassword {
        private UUID id;
        private String roomPassword;
    }
}
