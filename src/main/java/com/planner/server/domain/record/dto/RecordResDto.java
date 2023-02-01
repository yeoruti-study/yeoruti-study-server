package com.planner.server.domain.record.dto;

import com.planner.server.domain.record.entity.Record;
import com.planner.server.domain.user.dto.UserResDto;
import com.planner.server.domain.user_study_subject.dto.UserStudySubjectResDto;
import com.planner.server.domain.user_study_subject.entity.UserStudySubject;
import lombok.Builder;
import lombok.Getter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class RecordResDto {

    private UUID id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private UserResDto userDto;
    private UserStudySubjectResDto userStudySubjectDto;
    private Duration totalStudyTime;
    private boolean studying;

    public static RecordResDto toDto(Record record){
        return RecordResDto.builder()
                .id(record.getId())
                .startTime(record.getStartTime())
                .endTime(record.getEndTime())
                .userDto(UserResDto.toDto(record.getUser()))
                .userStudySubjectDto(UserStudySubjectResDto.toDto(record.getUserStudySubject()))
                .totalStudyTime(record.getTotalStudyTime())
                .studying(record.isStudying())
                .build();
    }
}
