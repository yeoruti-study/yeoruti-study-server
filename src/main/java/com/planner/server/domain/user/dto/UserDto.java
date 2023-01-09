package com.planner.server.domain.user.dto;

import com.planner.server.domain.user.entity.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {

    private Long cid;
    private String username;
    private String profile_name;
    private int profile_age;
    private String roles;

    public static UserDto toDto(User user) {

        return UserDto.builder()
                .cid(user.getCid())
                .username(user.getUsername())
                .profile_name(user.getProfileName())
                .profile_age(user.getProfileAge())
                .roles(user.getRoles())
                .build();
    }
}
