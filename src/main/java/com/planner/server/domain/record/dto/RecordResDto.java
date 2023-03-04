package com.planner.server.domain.record.dto;

import com.planner.server.domain.record.entity.Record;
import com.planner.server.domain.user.dto.UserResDto;
import com.planner.server.domain.user_study_subject.dto.UserStudySubjectResDto;
import com.planner.server.domain.user_study_subject.entity.UserStudySubject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class RecordResDto {

    private UUID id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private UserStudySubjectResDto userStudySubjectDto;
    private Duration totalStudyTime;
    private boolean studying;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResStartRecord {
        private UUID recordId;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResSearchListByUser {
        private UUID userId;
        private List<RecordResDto> recordList;
    }

    public static RecordResDto toDto(Record record){
        return RecordResDto.builder()
                .id(record.getId())
                .startTime(record.getStartTime())
                .endTime(record.getEndTime())
                .userStudySubjectDto(UserStudySubjectResDto.toDto(record.getUserStudySubject()))
                .totalStudyTime(record.getTotalStudyTime())
                .studying(record.isStudying())
                .build();
    }
}
