package com.planner.server.domain.friend.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class FriendReqDto {

    private UUID id;
    private UUID userId;
    private UUID friendId;
    private boolean allow;
    private LocalDateTime createdAt;

}
