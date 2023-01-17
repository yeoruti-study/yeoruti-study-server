package com.planner.server.domain.room_user.dto;

import java.util.UUID;

import com.planner.server.domain.room_user.entity.RoomUser;
import com.planner.server.domain.study_room.dto.StudyRoomDto;
import com.planner.server.domain.user.dto.UserDto;

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
public class RoomUserDto {
    private UUID id;
    private UserDto userDto;
    private StudyRoomDto studyRoomDto;

    public static RoomUserDto toDto(RoomUser entity) {
        UserDto userDto = UserDto.toDto(entity.getUser());
        StudyRoomDto studyRoomDto = StudyRoomDto.toDto(entity.getStudyRoom());

        return RoomUserDto.builder()
            .id(entity.getId())
            .userDto(userDto)
            .studyRoomDto(studyRoomDto)
            .build();
    }
}
