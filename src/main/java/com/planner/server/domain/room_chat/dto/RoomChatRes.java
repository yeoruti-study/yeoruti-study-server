package com.planner.server.domain.room_chat.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.planner.server.domain.room_chat.entity.RoomChat;
import com.planner.server.domain.study_room.dto.StudyRoomResDto;

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
public class RoomChatRes {
    private UUID id;
    private String content;
    private StudyRoomResDto studyRoomDto;
    private LocalDateTime createdAt;

    public static RoomChatRes toDto(RoomChat entity) {
        StudyRoomResDto studyRoomDto = StudyRoomResDto.toDto(entity.getStudyRoom());

        return RoomChatRes.builder()
            .id(entity.getId())
            .content(entity.getContent())
            .studyRoomDto(studyRoomDto)
            .createdAt(entity.getCreatedAt())
            .build();
    }
}
