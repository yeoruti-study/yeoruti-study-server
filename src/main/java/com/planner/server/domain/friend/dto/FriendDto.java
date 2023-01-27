package com.planner.server.domain.friend.dto;

import com.planner.server.domain.friend.entity.Friend;
import com.planner.server.domain.user.dto.UserResDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class FriendDto {

    private UUID id;
    private UserResDto user;
    private UserResDto friend;
    private boolean allow;
    private LocalDateTime createdAt;

    public static FriendDto toDto(Friend friend){
        return FriendDto.builder()
                .id(friend.getId())
                .user(UserResDto.toDto(friend.getUser()))
                .friend(UserResDto.toDto(friend.getFriend()))
                .allow(friend.isAllow())
                .createdAt(friend.getCreatedAt())
                .build();
    }

}
