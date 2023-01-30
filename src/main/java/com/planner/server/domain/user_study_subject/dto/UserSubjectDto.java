package com.planner.server.domain.user_study_subject.dto;

import com.planner.server.domain.user_study_subject.entity.UserStudySubject;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class UserSubjectDto{

    private UUID id;
    private String title;
    private UUID userId;

    public static UserSubjectDto toDto(UserStudySubject userStudySubject){
        return UserSubjectDto.builder()
                .id(userStudySubject.getId())
                .title(userStudySubject.getTitle())
                .userId(userStudySubject.getUser().getId())
                .build();
    }
}
