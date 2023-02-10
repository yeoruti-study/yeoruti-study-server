package com.planner.server.domain.record.controller;

import com.planner.server.domain.message.Message;
import com.planner.server.domain.record.dto.RecordReqDto;
import com.planner.server.domain.record.dto.RecordResDto;
import com.planner.server.domain.record.service.RecordService;
import com.planner.server.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/record")
public class RecordController {

    private final RecordService recordService;
    private final UserService userService;

    @PostMapping("/one")
    public ResponseEntity<?> createOne(@RequestBody RecordReqDto req){
        try {
            RecordResDto savedRecord = recordService.save(req);
        } catch (Exception e) {
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

    @GetMapping("/user/list")
    public ResponseEntity<?> SearchListByUser(@RequestParam("userId") UUID req){
        List<RecordResDto> recordResDtoList = null;
        try {
            recordResDtoList = recordService.findListByUser(req);
        } catch (Exception e) {
            Message message = Message.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message(e.getMessage())
                    .build();
            return new ResponseEntity<>(message, message.getStatus());
        }

        Message message = Message.builder()
                .data(recordResDtoList)
                .status(HttpStatus.OK)
                .message("success")
                .build();
        return new ResponseEntity<>(message, message.getStatus());
    }

    @GetMapping("/user-study-subject/list")
    public ResponseEntity<?> SearchListByUserStudySubject(@RequestParam("userStudySubjectId") UUID req){
        List<RecordResDto> recordResDtoList = null;
        try {
            recordResDtoList = recordService.getByUserStudySubjectId(req);
        } catch (Exception e) {
            Message message = Message.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message(e.getMessage())
                    .build();
            return new ResponseEntity<>(message, message.getStatus());
        }

        Message message = Message.builder()
                .data(recordResDtoList)
                .status(HttpStatus.OK)
                .message("success")
                .build();
        return new ResponseEntity<>(message, message.getStatus());
    }

    @DeleteMapping("/one")
    public ResponseEntity<?> deleteOne(@RequestBody RecordReqDto req){
        try {
            recordService.deleteOne(req);
        } catch (Exception e) {
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
