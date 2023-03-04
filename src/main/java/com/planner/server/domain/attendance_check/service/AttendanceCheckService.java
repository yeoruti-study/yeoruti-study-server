package com.planner.server.domain.attendance_check.service;

import com.planner.server.domain.attendance_check.dto.AttendanceCheckReqDto;
import com.planner.server.domain.attendance_check.dto.AttendanceCheckResDto;
import com.planner.server.domain.attendance_check.entity.AttendanceCheck;
import com.planner.server.domain.attendance_check.repository.AttendanceCheckRepository;
import com.planner.server.domain.user.entity.User;
import com.planner.server.domain.user.repository.UserRepository;
import com.planner.server.utils.SecurityContextHolderUtils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AttendanceCheckService {
    private final AttendanceCheckRepository attendanceCheckRepository;
    private final UserRepository userRepository;

    public void createOne() {
        UUID userId = SecurityContextHolderUtils.getUserId();
        Optional<User> userOpt = userRepository.findById(userId);

        if(userOpt.isPresent()) {
            if(this.isExistTodayAttendanceCheck(userId)) {
                return;
            }

            AttendanceCheck attendanceCheck = AttendanceCheck.builder()
                .id(UUID.randomUUID())
                .user(userOpt.get())
                .createdAt(LocalDateTime.now())
                .build();

            attendanceCheckRepository.save(attendanceCheck);
        }else {
            throw new NullPointerException("존재하지 않는 데이터");
        }
    }

    public List<AttendanceCheckResDto> searchListByUserId() {
        UUID userId = SecurityContextHolderUtils.getUserId();
        Optional<User> userOpt = userRepository.findById(userId);

        if(userOpt.isPresent()) {
            List<AttendanceCheck> attendanceChecks = attendanceCheckRepository.findByUserId(userId);
            List<AttendanceCheckResDto> attendanceCheckResDtos = attendanceChecks.stream().map(r -> AttendanceCheckResDto.toDto(r)).collect(Collectors.toList());
            return attendanceCheckResDtos;
        }else {
            throw new NullPointerException("존재하지 않는 데이터");
        }
    }

    // 이미 등록한 출석체크 데이터가 존재하는지 검사
    public boolean isExistTodayAttendanceCheck(UUID userId) {
        LocalDateTime todayStartTime = LocalDateTime.now().with(LocalTime.MIN);
        LocalDateTime todayEndTime = LocalDateTime.now().with(LocalTime.MAX);

        Optional<AttendanceCheck> attendanceCheckOpt = attendanceCheckRepository.findByDateRange(userId, todayStartTime, todayEndTime);
        if(attendanceCheckOpt.isPresent()) {
            return true;
        }else {
            return false;
        }
    }
}
