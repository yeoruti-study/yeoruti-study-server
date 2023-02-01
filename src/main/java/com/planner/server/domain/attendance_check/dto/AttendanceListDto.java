package com.planner.server.domain.attendance_check.dto;

import com.planner.server.domain.attendance_check.entity.AttendanceCheck;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class AttendanceListDto {

    private List<AttendanceResDto> attendanceDtos = new ArrayList<>();

    public static AttendanceListDto toDtoList(List<AttendanceCheck> attendances){
        List<AttendanceResDto> temp =new ArrayList<>();
        attendances.stream()
                .forEach(attendance -> temp.add(AttendanceResDto.toDto(attendance)));

        return AttendanceListDto.builder()
                .attendanceDtos(temp)
                .build();
    }
}
