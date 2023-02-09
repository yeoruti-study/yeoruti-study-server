package com.planner.server.domain.study_room.dto;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.planner.server.domain.room_chat.dto.RoomChatRes;
import com.planner.server.domain.study_category.dto.StudyCategoryReqDto;
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
public class StudyRoomReqDto {
    private UUID id;
    private String name;
    private int maximumNumberOfPeople;
    private Duration studyGoalTime;
    private String roomPassword;
    private UUID masterUserId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Getter
    @Builder
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class JoinStudyCategory {
        private UUID id;
        private String name;
        private StudyCategoryReqDto studyCategoryDto;
        private int maximumNumberOfPeople;
        private Duration studyGoalTime;
        private String roomPassword;
        private UUID masterUserId;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }

    @Getter
    @Builder
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReqChangePassword {
        private UUID id;
        private String roomPassword;
        private UUID userId;
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
