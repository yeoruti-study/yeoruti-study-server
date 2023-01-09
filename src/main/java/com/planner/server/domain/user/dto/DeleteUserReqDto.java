package com.planner.server.domain.user.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class DeleteUserReqDto {

    private UUID id;
    private String password;
}
