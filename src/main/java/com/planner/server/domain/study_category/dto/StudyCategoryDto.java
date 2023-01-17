package com.planner.server.domain.study_category.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.planner.server.domain.study_category.entity.StudyCategory;
import com.planner.server.domain.study_room.dto.StudyRoomDto;

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
    private List<StudyRoomDto> studyRoomDtos = new ArrayList<>();

    public static StudyCategoryDto toDto(StudyCategory entity) {
        List<StudyRoomDto> studyRoomDtos = entity.getStudyRooms().stream().map(room -> StudyRoomDto.toDto(room)).collect(Collectors.toList());
        
        return StudyCategoryDto.builder()
            .id(entity.getId())
            .name(entity.getName())
            .description(entity.getDescription())
            .studyRoomDtos(studyRoomDtos)
            .build();
    }
}
