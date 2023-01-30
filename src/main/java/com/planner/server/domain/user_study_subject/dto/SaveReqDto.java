package com.planner.server.domain.user_study_subject.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class SaveReqDto {

    private String title;
    private UUID userId;
}
