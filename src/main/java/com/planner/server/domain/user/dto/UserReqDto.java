package com.planner.server.domain.user.dto;

import com.planner.server.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import java.util.UUID;

@Getter
@Builder
public class UserReqDto {

    private UUID id;
    private String username;
    private String password;
    private String roles;
    private String profileName;
    private String profileAge;
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
                .profileAge(user.getProfileAge())
                .alarmPermission(user.isAlarmPermission())
                .friendAcceptance(user.isFriendAcceptance())
                .profileImagePath(user.getProfileImagePath())
                .build();
    }
}
