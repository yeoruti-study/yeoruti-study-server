package com.planner.server.domain.study_category.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class StudyCategoryDto {
    private UUID id;
    private String name;
    private String description;
}
