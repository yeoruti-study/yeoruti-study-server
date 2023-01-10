package com.planner.server.domain.friend.dto;

import com.planner.server.domain.user.dto.UserDto;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class SaveFriendReqDto {

    private String userProfileName;
    private String friendProfileName;

}
