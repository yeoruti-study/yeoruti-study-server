package com.planner.server.domain.record.dto;

import com.planner.server.domain.record.entity.Record;
import lombok.Builder;
import lombok.Getter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class RecordReqDto {

    private UUID id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private UUID userId;
    private UUID userStudySubjectId;
    private boolean studying;

    public static RecordReqDto toDto(Record record){
        return RecordReqDto.builder()
                .id(record.getId())
                .startTime(record.getStartTime())
                .endTime(record.getEndTime())
                .userId(record.getUser().getId())
                .userStudySubjectId(record.getUserStudySubject().getId())
                .studying(record.isStudying())
                .build();
    }
}
