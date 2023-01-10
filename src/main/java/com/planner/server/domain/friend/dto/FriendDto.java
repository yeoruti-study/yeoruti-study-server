package com.planner.server.domain.friend.dto;

import com.planner.server.domain.friend.entity.Friend;
import com.planner.server.domain.user.dto.UserDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
public class FriendDto {

    private UserDto user;
    private UserDto friend;
    private boolean allow;
    private LocalDateTime createdAt;

    public static FriendDto toDto(Friend friend){
        return FriendDto.builder()
                .user(UserDto.toDto(friend.getUser()))
                .friend(UserDto.toDto(friend.getFriend()))
                .allow(friend.isAllow())
                .createdAt(friend.getCreatedAt())
                .build();
    }

}
