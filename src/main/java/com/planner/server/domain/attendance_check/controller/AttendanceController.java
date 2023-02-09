package com.planner.server.domain.attendance_check.controller;

import com.planner.server.domain.attendance_check.dto.AttendanceReqDto;
import com.planner.server.domain.attendance_check.dto.AttendanceResDto;
import com.planner.server.domain.attendance_check.service.AttendanceService;
import com.planner.server.domain.message.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @PostMapping("/one")
    public ResponseEntity<?> save(@RequestBody AttendanceReqDto req){
        AttendanceResDto attendanceDto;
        try{
            attendanceDto = attendanceService.save(req.getUserId());
        } catch (Exception e) {
            Message message = Message.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message(e.getMessage())
                    .build();
            return new ResponseEntity<>(message, message.getStatus());
        }
        Message message = Message.builder()
                .data(attendanceDto)
                .status(HttpStatus.OK)
                .message("success")
                .build();
        return new ResponseEntity<>(message, message.getStatus());
    }

    @GetMapping("/user/list")
    public ResponseEntity<?> findByUserId(@RequestParam UUID userId){
        List<AttendanceResDto> attendanceResDtos = new ArrayList<>();
        try{
            attendanceResDtos = attendanceService.findByUserId(userId);
        }
        catch (NoSuchElementException e){
            Message message = Message.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message(e.getMessage())
                    .build();
            return new ResponseEntity<>(message, message.getStatus());
        }

        Message message = Message.builder()
                .data(attendanceResDtos)
                .status(HttpStatus.OK)
                .message("success")
                .build();
        return new ResponseEntity<>(message, message.getStatus());
    }

    @DeleteMapping("/one")
    public ResponseEntity<?> deleteById(@RequestBody AttendanceReqDto req){
        try{
            attendanceService.deleteById(req.getId());
        }
        catch (NoSuchElementException e){
            Message message = Message.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message(e.getMessage())
                    .build();
            return new ResponseEntity<>(message, message.getStatus());
        }
        Message message = Message.builder()
                .status(HttpStatus.OK)
                .message("success")
                .build();
        return new ResponseEntity<>(message, message.getStatus());
    }
}
