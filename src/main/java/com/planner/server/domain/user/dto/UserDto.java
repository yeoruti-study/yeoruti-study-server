package com.planner.server.domain.user.dto;

import java.util.UUID;

import com.planner.server.domain.user.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private UUID id;

    public static UserDto toDto(User entity) {
        return UserDto.builder()
            .id(entity.getId())
            .build();
    }
}
