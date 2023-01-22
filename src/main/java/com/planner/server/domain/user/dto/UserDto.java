package com.planner.server.domain.user.dto;

import com.planner.server.domain.user.entity.User;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class UserDto {

    private UUID id;
    private String username;
    private String roles;
    private String profileName;
    private String profileAge;
    private String profileImagePath;
    private boolean alarmPermission;
    private boolean friendAcceptance;

    public static UserDto toDto(User user) {

        return UserDto.builder()
                .username(user.getUsername())
                .profileName(user.getProfileName())
                .profileAge(user.getProfileAge())
                .alarmPermission(user.isAlarmPermission())
                .friendAcceptance(user.isFriendAcceptance())
                .profileImagePath(user.getProfileImagePath())
                .build();
    }
}
