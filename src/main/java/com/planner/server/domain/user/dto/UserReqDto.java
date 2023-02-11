package com.planner.server.domain.user.dto;

import com.planner.server.domain.user.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserReqDto {

    private UUID id;
    private String username;
    private String password;
    private String roles;
    private String profileName;
    private String profileBirth;
    private String profileImagePath;
    private boolean alarmPermission;
    private boolean friendAcceptance;

    public static UserReqDto toDto(User user) {

        return UserReqDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRoles())
                .profileName(user.getProfileName())
                .profileBirth(user.getProfileBirth())
                .alarmPermission(user.isAlarmPermission())
                .friendAcceptance(user.isFriendAcceptance())
                .profileImagePath(user.getProfileImagePath())
                .build();
    }
}
