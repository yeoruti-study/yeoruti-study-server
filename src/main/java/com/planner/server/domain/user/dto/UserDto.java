package com.planner.server.domain.user.dto;

import com.planner.server.domain.user.entity.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {

    private String username;
    private String profileName;
    private int profileAge;

    public static UserDto toDto(User user) {

        return UserDto.builder()
                .username(user.getUsername())
                .profileName(user.getProfileName())
                .profileAge(user.getProfileAge())
                .build();
    }
}
