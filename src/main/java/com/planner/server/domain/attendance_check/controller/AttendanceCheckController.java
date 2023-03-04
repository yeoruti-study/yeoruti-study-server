package com.planner.server.domain.attendance_check.controller;

import com.planner.server.domain.attendance_check.service.AttendanceCheckService;
import com.planner.server.domain.message.Message;
import com.planner.server.utils.SecurityContextHolderUtils;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/attendance-check")
@RequiredArgsConstructor
public class AttendanceCheckController {
    private final AttendanceCheckService attendanceCheckService;

    @PostMapping("/one")
    public ResponseEntity<?> createOne(){
        Message message = new Message();
        UUID userId = SecurityContextHolderUtils.getUserId();

        try {
            attendanceCheckService.createOne(userId);
            message.setStatus(HttpStatus.OK);
            message.setMessage("success");
        } catch (Exception e) {
            message.setStatus(HttpStatus.BAD_REQUEST);
            message.setMessage("error");
            message.setMemo(e.getMessage());
        }
        return new ResponseEntity<>(message, message.getStatus());
    }

    @GetMapping("/list")
    public ResponseEntity<?> searchListByUser(){
        Message message = new Message();
        UUID userId = SecurityContextHolderUtils.getUserId();

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

  
}
