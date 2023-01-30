package com.planner.server.domain.friend.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class SaveReqDto {

    private UUID userId;
    private UUID friendId;

}
