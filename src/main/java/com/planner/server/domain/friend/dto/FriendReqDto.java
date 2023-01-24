package com.planner.server.domain.friend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FriendReqDto {

    private String userProfileName;
    private String friendProfileName;

}