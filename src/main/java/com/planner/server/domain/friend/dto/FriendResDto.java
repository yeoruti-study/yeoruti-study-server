package com.planner.server.domain.friend.dto;

import com.planner.server.domain.friend.entity.Friend;
import com.planner.server.domain.user.entity.User;
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
public class FriendResDto {

    private String username;
    private String profileName;
    private LocalDateTime createdAt;

    public static FriendResDto toDto(Friend friend){
        User friendEntity = friend.getFriend();
        return FriendResDto.builder()
                .username(friendEntity.getUsername())
                .profileName(friendEntity.getProfileName())
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FriendRequest{
        private UUID id;
        private String username;
        private LocalDateTime createdAt;

        public static FriendResDto.FriendRequest toDto(Friend friend){
            return FriendResDto.FriendRequest.builder()
                    .id(friend.getId())
                    .username(friend.getFriend().getUsername())
                    .createdAt(LocalDateTime.now())
                    .build();
        }
    }

}