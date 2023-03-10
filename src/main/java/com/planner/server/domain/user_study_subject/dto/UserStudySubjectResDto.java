package com.planner.server.domain.user_study_subject.dto;

import com.planner.server.domain.user_study_subject.entity.UserStudySubject;
import lombok.Builder;
import lombok.Getter;

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

}
