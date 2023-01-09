package com.planner.server.domain.user.dto;

import com.planner.server.domain.user.entity.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SaveUserResDto {

    private Long cid;
    private String username;
    private String profileName;

    public static SaveUserResDto toDto(User user){
        return SaveUserResDto.builder()
                .cid(user.getCid())
                .username(user.getUsername())
                .profileName(user.getProfileName())
                .build();
    }
}
