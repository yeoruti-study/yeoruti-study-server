package com.planner.server.domain.attendance_check.service;

import com.planner.server.domain.attendance_check.dto.AttendanceDto;
import com.planner.server.domain.attendance_check.dto.AttendanceListDto;
import com.planner.server.domain.attendance_check.entity.AttendanceCheck;
import com.planner.server.domain.attendance_check.repository.AttendanceRepository;
import com.planner.server.domain.user.dto.UserDto;
import com.planner.server.domain.user.entity.User;
import com.planner.server.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final UserRepository userRepository;
    public AttendanceDto save(UUID userId) {
        Optional<User> findUser = userRepository.findById(userId);
        if(!findUser.isPresent()){
            throw new NoSuchElementException("[id] 확인 요망.");
        }
        User user = findUser.get();

        AttendanceCheck attendanceCheck = AttendanceCheck.builder()
                .id(UUID.randomUUID())
                .user(user)
                .createdAt(LocalDateTime.now())
                .build();
        AttendanceCheck saveAttendance = attendanceRepository.save(attendanceCheck);
        return AttendanceDto.toDto(saveAttendance, UserDto.toDto(user));
    }

    public AttendanceListDto findByUserId(UUID userId) {
        List<AttendanceCheck> attendanceList;
        try{
            attendanceList = attendanceRepository.findByUserId(userId);
        }
        catch (NoSuchElementException e){
            throw new NoSuchElementException("[id]값 확인 요망.");
        }
        AttendanceListDto attendanceResDtoList = AttendanceListDto.toDtoList(attendanceList);
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
