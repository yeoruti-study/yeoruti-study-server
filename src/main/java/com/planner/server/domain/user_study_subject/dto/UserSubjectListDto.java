package com.planner.server.domain.user_study_subject.dto;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class UserSubjectListDto {

    private List<UserSubjectDto> userStudySubjectDtoList = new ArrayList<>();

    public static UserSubjectListDto toDto(List<UserSubjectDto> userStudySubjectDtos){
        UserSubjectListDto build = UserSubjectListDto.builder().build();
        List<UserSubjectDto> tmp = new ArrayList<>();
        userStudySubjectDtos.forEach(dto -> tmp.add(dto));

        build.setUserStudySubjectDtoList(tmp);
        return build;
    }
}
