package com.planner.server.domain.friend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FriendReqDto {

    private UUID id;
    private UUID userId;
    private UUID friendId;
    private boolean allow;
    private LocalDateTime createdAt;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReqCreateOne{
        private String friendUsername;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReqAccept{
        private UUID id;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReqDeleteOne{
        private UUID id;
    }

}