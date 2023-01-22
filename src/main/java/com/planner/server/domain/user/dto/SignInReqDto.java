package com.planner.server.domain.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignInReqDto {

    private String username;
    private String password;
}
