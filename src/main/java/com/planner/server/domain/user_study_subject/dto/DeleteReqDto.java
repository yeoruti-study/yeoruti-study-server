package com.planner.server.domain.user_study_subject.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class DeleteReqDto {

    private UUID id;
    private UUID userId;
}
