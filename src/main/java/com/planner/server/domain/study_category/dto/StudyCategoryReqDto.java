package com.planner.server.domain.study_category.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.planner.server.domain.study_category.entity.StudyCategory;
import com.planner.server.domain.study_room.dto.StudyRoomResDto;

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
public class StudyCategoryReqDto {
    private UUID id;
    private String name;
    private String description;
}
