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

    // @Getter
    // @Builder
    // @ToString
    // @AllArgsConstructor
    // @NoArgsConstructor
    // public static class JoinStudyRoom {
    //     private UUID id;
    //     private String name;
    //     private String description;
    //     private List<StudyRoomResDto> studyRoomDtos = new ArrayList<>();

    //     public static StudyCategoryAndStudyRoom toDto(StudyCategory entity) {
    //         List<StudyRoomResDto> studyRoomDtos = entity.getStudyRooms().stream().map(room -> StudyRoomResDto.toDto(room)).collect(Collectors.toList());
            
    //         return StudyCategoryAndStudyRoom.builder()
    //             .id(entity.getId())
    //             .name(entity.getName())
    //             .description(entity.getDescription())
    //             .studyRoomDtos(studyRoomDtos)
    //             .build();
    //     }
    // }
}
