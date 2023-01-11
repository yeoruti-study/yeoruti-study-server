package com.planner.server.domain.attendance_check.service;

import com.planner.server.domain.attendance_check.dto.AttendanceDto;
import com.planner.server.domain.attendance_check.dto.AttendanceResDtoList;
import com.planner.server.domain.attendance_check.dto.AttendanceResDto;
import com.planner.server.domain.attendance_check.entity.AttendanceCheck;
import com.planner.server.domain.attendance_check.repository.AttendanceRepository;
import com.planner.server.domain.user.dto.UserDto;
import com.planner.server.domain.user.entity.User;
import com.planner.server.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final UserRepository userRepository;
    public AttendanceDto saveByUserId(String userId) {
        User user = userRepository.findById(UUID.fromString(userId)).get();

        UUID id = UUID.randomUUID();

        AttendanceCheck attendanceCheck = AttendanceCheck.builder()
                .id(id)
                .user(user)
                .createdAt(LocalDateTime.now())
                .build();
        AttendanceCheck saveAttendance = attendanceRepository.save(attendanceCheck);
        return AttendanceDto.toDto(saveAttendance, UserDto.toDto(user));
    }

    public AttendanceResDtoList findByUserId(String userId) {
        List<AttendanceCheck> attendanceList = attendanceRepository.findByUserId(UUID.fromString(userId));
        AttendanceResDtoList attendanceResDtoList = AttendanceResDtoList.toDtoList(attendanceList);
        return attendanceResDtoList;
    }

    // 쿼리가 2번 생성됨. (수정 필요)
    public void deleteById(String id) {
        AttendanceCheck attendanceCheck = attendanceRepository.findById(UUID.fromString(id)).get();
        attendanceRepository.delete(attendanceCheck);
    }
}
