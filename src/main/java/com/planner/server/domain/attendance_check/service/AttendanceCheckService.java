package com.planner.server.domain.attendance_check.service;

import com.planner.server.domain.attendance_check.dto.AttendanceCheckResDto;
import com.planner.server.domain.attendance_check.entity.AttendanceCheck;
import com.planner.server.domain.attendance_check.repository.AttendanceCheckRepository;
import com.planner.server.domain.user.entity.User;
import com.planner.server.domain.user.repository.UserRepository;
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

    public void createOne(UUID userId) throws Exception {
        Optional<User> findUser = userRepository.findById(userId);

        if(!findUser.isPresent()){
            throw new Exception("유저가 존재하지 않습니다.");
        }

        if(this.todayAttendanceExists(userId)) {
            throw new Exception("이미 오늘은 출석체크가 완료되었습니다.");
        }
        AttendanceCheck attendanceCheck = AttendanceCheck.builder()
                .id(UUID.randomUUID())
                .user(findUser.get())
                .createdAt(LocalDateTime.now())
                .build();
        attendanceCheckRepository.save(attendanceCheck);
    }

    public List<AttendanceCheckResDto> searchListByUserId(UUID userId) {
        Optional<User> userOpt = userRepository.findById(userId);

        if(userOpt.isPresent()) {
            List<AttendanceCheck> attendanceChecks = attendanceCheckRepository.findByUserId(userId);
            List<AttendanceCheckResDto> attendanceCheckResDtos = attendanceChecks.stream().map(r -> AttendanceCheckResDto.toDto(r)).collect(Collectors.toList());
            return attendanceCheckResDtos;
        }else {
            throw new NullPointerException("존재하지 않는 데이터");
        }
    }

    public void deleteOne(UUID attendanceCheckId) {
        Optional<AttendanceCheck> attendanceCheckOpt = attendanceCheckRepository.findById(attendanceCheckId);

        if(attendanceCheckOpt.isPresent()) {
            attendanceCheckRepository.delete(attendanceCheckOpt.get());
        } else {
            throw new NullPointerException("존재하지 않는 데이터");
        }
    }

    // 이미 등록한 출석체크 데이터가 존재하는지 검사
    public boolean todayAttendanceExists(UUID userId) {
        LocalDateTime todayStartTime = LocalDateTime.now().with(LocalTime.MIN);
        LocalDateTime todayEndTime = LocalDateTime.now().with(LocalTime.MAX);

        Optional<AttendanceCheck> attendanceCheckOpt = attendanceCheckRepository.findByDateRange(userId, todayStartTime, todayEndTime);
        if(attendanceCheckOpt.isPresent())
            return true;
        else
            return false;
    }

    public void deleteTodayAttendance(UUID userId) throws Exception {
        LocalDateTime todayStartTime = LocalDateTime.now().with(LocalTime.MIN);
        LocalDateTime todayEndTime = LocalDateTime.now().with(LocalTime.MAX);

        Optional<AttendanceCheck> attendanceCheckOpt = attendanceCheckRepository.findByDateRange(userId, todayStartTime, todayEndTime);

        if(attendanceCheckOpt.isPresent())
            attendanceCheckRepository.delete(attendanceCheckOpt.get());
        else{
            throw new Exception("오늘은 출석체크를 하지 않았습니다. 출석체크를 지울 수 없음");
        }
    }
}
