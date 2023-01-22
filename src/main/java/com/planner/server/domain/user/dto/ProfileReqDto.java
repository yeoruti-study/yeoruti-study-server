package com.planner.server.domain.user.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ProfileReqDto {

    private UUID id;
    private String profileName;
    private String profileAge;
    private String profileImagePath;
    private boolean friendAcceptance;
    private boolean alarmPermission;

}
