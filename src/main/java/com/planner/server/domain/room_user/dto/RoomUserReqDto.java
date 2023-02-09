package com.planner.server.domain.room_user.dto;

import java.util.UUID;

import com.planner.server.domain.room_user.entity.RoomUser;
import com.planner.server.domain.study_room.dto.StudyRoomResDto;

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
public class RoomUserReqDto {
    private UUID id;
    private UserDto userDto;
    private StudyRoomResDto studyRoomDto;

    public static RoomUserReqDto toDto(RoomUser entity) {
        UserDto userDto = UserDto.toDto(entity.getUser());
        StudyRoomResDto studyRoomDto = StudyRoomResDto.toDto(entity.getStudyRoom());

        return RoomUserReqDto.builder()
            .id(entity.getId())
            .userDto(userDto)
            .studyRoomDto(studyRoomDto)
            .build();
    }
}
