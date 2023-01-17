package com.planner.server.domain.room_chat.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.planner.server.domain.room_chat.entity.RoomChat;
import com.planner.server.domain.study_room.dto.StudyRoomDto;

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
public class RoomChatDto {
    private UUID id;
    private String content;
    private StudyRoomDto studyRoomDto;
    private LocalDateTime createdAt;

    public static RoomChatDto toDto(RoomChat entity) {
        StudyRoomDto studyRoomDto = StudyRoomDto.toDto(entity.getStudyRoom());

        return RoomChatDto.builder()
            .id(entity.getId())
            .content(entity.getContent())
            .studyRoomDto(studyRoomDto)
            .createdAt(entity.getCreatedAt())
            .build();
    }
}
