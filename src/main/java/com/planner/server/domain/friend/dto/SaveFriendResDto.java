package com.planner.server.domain.friend.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class SaveFriendResDto {

    private Long cid;
    private String userProfileName;
    private String friendProfileName;
    private LocalDateTime createdAt;

}
