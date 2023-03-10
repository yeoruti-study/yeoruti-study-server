package com.planner.server.domain.room_user.dto;

import java.util.UUID;

import com.planner.server.domain.room_user.entity.RoomUser;
import com.planner.server.domain.study_room.dto.StudyRoomResDto;

import com.planner.server.domain.user.dto.UserResDto;

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
public class RoomUserResDto {
    private UUID id;
    private UserResDto userDto;
    private StudyRoomResDto studyRoomDto;

    public static RoomUserResDto toDto(RoomUser entity) {
        UserResDto userDto = UserResDto.toDto(entity.getUser());
        StudyRoomResDto studyRoomDto = StudyRoomResDto.toDto(entity.getStudyRoom());

        return RoomUserResDto.builder()
            .id(entity.getId())
            .userDto(userDto)
            .studyRoomDto(studyRoomDto)
            .build();
    }
}
