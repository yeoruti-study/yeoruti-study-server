package com.planner.server.domain.user_study_subject.dto;

import com.planner.server.domain.user_study_subject.entity.UserStudySubject;
import lombok.*;

import java.util.UUID;

@Data
@Builder
public class UserStudySubjectReqDto {

    private UUID id;
    private String title;
    private UUID userId;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReqCreateOne{
        private String title;
    }

    public static UserStudySubjectReqDto toDto(UserStudySubject userStudySubject){
        return UserStudySubjectReqDto.builder()
                .id(userStudySubject.getId())
                .title(userStudySubject.getTitle())
                .userId(userStudySubject.getUser().getId())
                .build();
    }
}
