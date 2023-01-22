package com.planner.server.domain.user.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class SignUpReqDto {

    private UUID id;
    private String username;
    private String password;
    private String roles;
    private String profileName;
    private String profileAge;
    private String profileImagePath;
    private boolean alarmPermission;
    private boolean friendAcceptance;

}
