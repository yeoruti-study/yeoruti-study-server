package com.planner.server.domain.user_study_subject.dto;

import com.planner.server.domain.user.dto.UserResDto;
import com.planner.server.domain.user_study_subject.entity.UserStudySubject;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class UserStudySubjectResDto {

    private UUID id;
    private String title;
    private UserResDto userDto;

    public static UserStudySubjectResDto toDto(UserStudySubject userStudySubject){
        return UserStudySubjectResDto.builder()
                .id(userStudySubject.getId())
                .title(userStudySubject.getTitle())
                .userDto(UserResDto.toDto(userStudySubject.getUser()))
                .build();
    }
}
