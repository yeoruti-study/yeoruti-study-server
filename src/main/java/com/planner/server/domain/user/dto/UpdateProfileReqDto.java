package com.planner.server.domain.user.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class UpdateProfileReqDto {

    private UUID id;
    private String profileName;
    private int profileAge;
    private String profileImagePath;
    private boolean friendAcceptance;
    private boolean alarmPermission;

}
