package com.planner.server.domain.user.dto;

import com.planner.server.domain.user.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResDto {

    private UUID id;
    private String username;
    private String roles;
    private String profileName;
    private String profileBirth;
    private String profileImagePath;
    private boolean alarmPermission;
    private boolean friendAcceptance;

    public static UserResDto toDto(User user) {

        return UserResDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .roles(user.getRoles())
                .profileName(user.getProfileName())
                .profileBirth(user.getProfileBirth())
                .alarmPermission(user.isAlarmPermission())
                .friendAcceptance(user.isFriendAcceptance())
                .profileImagePath(user.getProfileImagePath())
                .build();
    }
}
