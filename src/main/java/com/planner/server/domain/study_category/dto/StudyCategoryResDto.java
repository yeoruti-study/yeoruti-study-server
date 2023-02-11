package com.planner.server.domain.study_category.dto;

import java.util.UUID;

import com.planner.server.domain.study_category.entity.StudyCategory;

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
public class StudyCategoryResDto {
    private UUID id;
    private String name;
    private String description;

    public static StudyCategoryResDto toDto(StudyCategory entity) {
        return StudyCategoryResDto.builder()
            .id(entity.getId())
            .name(entity.getName())
            .description(entity.getDescription())
            .build();
    }
}
