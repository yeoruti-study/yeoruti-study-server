package com.planner.server.domain.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SaveUserReqDto {

    private String username;
    private String password;

    private String profileName;
    private int profileAge;
    private String profileImagePath;

    private boolean alarmPermission;
    private boolean friendAcceptance;

}
