package com.planner.server.domain.user.dto;

import com.planner.server.domain.user.entity.User;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserReqDto {

    private String username;
    private String password;
    private String roles;
    private String profileName;
    private String profileBirth;
    private String profileImagePath;
    private boolean alarmPermission;
    private boolean friendAcceptance;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReqCreateOne{
        private String username;
        private String password;
        private String profileName;
        private String profileBirth;
        private String profileImagePath;
        private boolean alarmPermission;
        private boolean friendAcceptance;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReqUpdateProfile{
        private String profileName;
        private String profileBirth;
        private boolean friendAcceptance;
        private boolean alarmPermission;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReqDeleteUser{
        private String username;
        private String password;
    }

    public static UserReqDto toDto(User user) {

        return UserReqDto.builder()
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
