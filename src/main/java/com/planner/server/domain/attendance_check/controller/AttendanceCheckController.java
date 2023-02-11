package com.planner.server.domain.attendance_check.controller;

import com.planner.server.domain.attendance_check.dto.AttendanceCheckReqDto;
import com.planner.server.domain.attendance_check.service.AttendanceCheckService;
import com.planner.server.domain.message.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/attendance-check")
@RequiredArgsConstructor
public class AttendanceCheckController {
    private final AttendanceCheckService attendanceCheckService;

    @PostMapping("/one")
    public ResponseEntity<?> createOne(@RequestBody AttendanceCheckReqDto attendanceCheckDto){
        Message message = new Message();

        try {
            attendanceCheckService.createOne(attendanceCheckDto);
            message.setStatus(HttpStatus.OK);
            message.setMessage("success");
        } catch (Exception e) {
            message.setStatus(HttpStatus.BAD_REQUEST);
            message.setMessage("error");
            message.setMemo(e.getMessage());
        }
        return new ResponseEntity<>(message, message.getStatus());
    }

    @GetMapping("/list/user/{userId}")
    public ResponseEntity<?> searchListByUserId(@PathVariable(name = "userId") UUID userId){
        Message message = new Message();

        try {
            message.setData(attendanceCheckService.searchListByUserId(userId));
            message.setStatus(HttpStatus.OK);
            message.setMessage("success");
        } catch (Exception e) {
            message.setStatus(HttpStatus.BAD_REQUEST);
            message.setMessage("error");
            message.setMemo(e.getMessage());
        }
        return new ResponseEntity<>(message, message.getStatus());
    }

    @DeleteMapping("/one/{attendanceCheckId}")
    public ResponseEntity<?> deleteOne(@PathVariable(name = "attendanceCheckId") UUID userId){
        Message message = new Message();

        try {
            attendanceCheckService.deleteOne(userId);
            message.setStatus(HttpStatus.OK);
            message.setMessage("success");
        } catch (Exception e) {
            message.setStatus(HttpStatus.BAD_REQUEST);
            message.setMessage("error");
            message.setMemo(e.getMessage());
        }
        return new ResponseEntity<>(message, message.getStatus());
    }
}
