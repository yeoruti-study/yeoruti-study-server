package com.planner.server.domain.room_user.dto;

import java.util.UUID;

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
}
