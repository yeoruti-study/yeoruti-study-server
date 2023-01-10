package com.planner.server.domain.study_room.dto;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

import com.planner.server.domain.study_category.dto.StudyCategoryDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudyRoomDto {
    private UUID id;
    private String name;
    private StudyCategoryDto studyCategoryDto;
    private int maximumNumberOfPeople;
    private Duration studyGoalTime;
    private String roomPassword;
    private UUID masterUserId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    // TODO :: DTO생성되면 넣어야할 객체들
    // private List<RoomUser> roomUsers = new ArrayList<>();
    // private List<RoomChat> roomChats = new ArrayList<>();
}
