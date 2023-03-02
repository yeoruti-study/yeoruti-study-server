package com.planner.server.domain.user_study_subject.dto;

import com.planner.server.domain.user_study_subject.entity.UserStudySubject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class UserStudySubjectResDto {

    private UUID id;
    private String title;

    public static UserStudySubjectResDto toDto(UserStudySubject userStudySubject){
        return UserStudySubjectResDto.builder()
                .id(userStudySubject.getId())
                .title(userStudySubject.getTitle())
                .build();
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResSearchList {
        private UUID userId;
        private List<UserStudySubjectResDto> userStudySubjectResDtoList;
    }
}
