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
    public ResponseEntity<?> startRecord(@RequestBody RecordReqDto req){
        UUID recordId = null;
        try {
            recordId = recordService.startRecording(req);
        } catch (Exception e) {
            Message message = Message.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message("error")
                    .memo(e.getMessage())
                    .build();
            return new ResponseEntity<>(message, message.getStatus());
        }

        Message message = Message.builder()
                .data(recordId)
                .status(HttpStatus.OK)
                .message("success")
                .build();
        return new ResponseEntity<>(message, message.getStatus());
    }

    @PatchMapping("/one")
    public ResponseEntity<?> endRecord(@RequestBody RecordReqDto req){
        try {
            recordService.endRecording(req);
        } catch (Exception e) {
            Message message = Message.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message("error")
                    .memo(e.getMessage())
                    .build();
            return new ResponseEntity<>(message, message.getStatus());
        }

        Message message = Message.builder()
                .status(HttpStatus.OK)
                .message("success")
                .build();
        return new ResponseEntity<>(message, message.getStatus());
    }

    @GetMapping("/list/user/{userId}")
    public ResponseEntity<?> searchListByUser(@PathVariable("userId") UUID userId){
        List<RecordResDto> recordResDtoList = null;
        try {
            recordResDtoList = recordService.findListByUser(userId);
        } catch (Exception e) {
            Message message = Message.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message("error")
                    .memo(e.getMessage())
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

    @GetMapping("/list/user-study-subject/{userStudySubjectId}")
    public ResponseEntity<?> searchListByUserStudySubject(@PathVariable("userStudySubjectId") UUID userStudySubjectId){
        List<RecordResDto> recordResDtoList = null;
        try {
            recordResDtoList = recordService.getByUserStudySubjectId(userStudySubjectId);
        } catch (Exception e) {
            Message message = Message.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message("error")
                    .memo(e.getMessage())
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

    @DeleteMapping("/one/{recordId}")
    public ResponseEntity<?> deleteOne(@PathVariable("recordId") UUID recordId){
        try {
            recordService.deleteOne(recordId);
        } catch (Exception e) {
            Message message = Message.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message("error")
                    .memo(e.getMessage())
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
