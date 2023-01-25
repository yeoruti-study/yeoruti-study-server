package com.planner.server.domain.attendance_check.controller;

import com.planner.server.domain.attendance_check.dto.AttendanceDto;
import com.planner.server.domain.attendance_check.dto.AttendanceListDto;
import com.planner.server.domain.attendance_check.dto.AttendanceReqDto;
import com.planner.server.domain.attendance_check.dto.DeleteReqDto;
import com.planner.server.domain.attendance_check.service.AttendanceService;
import com.planner.server.domain.message.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody AttendanceReqDto req){
        AttendanceDto attendanceDto;
        try{
            attendanceDto = attendanceService.save(req.getUserId());
        }
        catch (NoSuchElementException e){
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

    @GetMapping("")
    public ResponseEntity<?> findByUserId(@RequestParam UUID userId){
        AttendanceListDto attendanceListDto;
        try{
            attendanceListDto = attendanceService.findByUserId(userId);
        }
        catch (NoSuchElementException e){
            Message message = Message.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message(e.getMessage())
                    .build();
            return new ResponseEntity<>(message, message.getStatus());
        }

        Message message = Message.builder()
                .data(attendanceListDto)
                .status(HttpStatus.OK)
                .message("success")
                .build();
        return new ResponseEntity<>(message, message.getStatus());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteById(@RequestBody DeleteReqDto req){
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
