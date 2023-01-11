package com.planner.server.domain.attendance_check.controller;

import com.planner.server.domain.attendance_check.dto.AttendanceDto;
import com.planner.server.domain.attendance_check.dto.AttendanceResDtoList;
import com.planner.server.domain.attendance_check.dto.AttendanceReqDto;
import com.planner.server.domain.attendance_check.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @PostMapping("")
    public AttendanceDto save(@RequestBody AttendanceReqDto req){
        return attendanceService.saveByUserId(req.getUserId());
    }

    @GetMapping("")
    public AttendanceResDtoList findByUser(@RequestParam String userId){
        return attendanceService.findByUserId(userId);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") String id){
        attendanceService.deleteById(id);
        return "SUCCESS";
    }
}
