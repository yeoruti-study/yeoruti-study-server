package com.planner.server.domain.attendance_check.service;

import com.planner.server.domain.attendance_check.dto.AttendanceResDto;
import com.planner.server.domain.attendance_check.entity.AttendanceCheck;
import com.planner.server.domain.attendance_check.repository.AttendanceRepository;
import com.planner.server.domain.user.entity.User;
import com.planner.server.domain.user.repository.UserRepository;
import com.planner.server.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public AttendanceResDto save(UUID userId) throws Exception {
        User findUser = userService.findOne(userId);

        AttendanceCheck attendanceCheck = AttendanceCheck.builder()
                .id(UUID.randomUUID())
                .user(findUser)
                .createdAt(LocalDateTime.now())
                .build();
        AttendanceCheck saveAttendance = attendanceRepository.save(attendanceCheck);

        return AttendanceResDto.toDto(saveAttendance);
    }

    public List<AttendanceResDto> findByUserId(UUID userId) {
        List<AttendanceResDto> attendanceResDtoList = new ArrayList<>();
        List<AttendanceCheck> attendanceList = new ArrayList<>();
        try{
            attendanceList = attendanceRepository.findByUserId(userId);
        }
        catch (NoSuchElementException e){
            throw new NoSuchElementException("[id]값 확인 요망.");
        }
        attendanceList.forEach(attendanceCheck -> attendanceResDtoList.add(AttendanceResDto.toDto(attendanceCheck)));
        return attendanceResDtoList;
    }

    // 쿼리가 2번 생성됨. (수정 필요)
    public void deleteById(UUID id) {
        Optional<AttendanceCheck> byId = attendanceRepository.findById(id);
        if(!byId.isPresent()){
            throw new NoSuchElementException("[id]값 확인 요망");
        }
        AttendanceCheck attendanceCheck = byId.get();
        attendanceRepository.delete(attendanceCheck);
    }
}
