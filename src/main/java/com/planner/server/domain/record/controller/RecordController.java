package com.planner.server.domain.record.controller;

import com.planner.server.domain.message.Message;
import com.planner.server.domain.record.dto.RecordReqDto;
import com.planner.server.domain.record.dto.RecordResDto;
import com.planner.server.domain.record.service.RecordService;
import com.planner.server.domain.user.service.UserService;
import com.planner.server.utils.SecurityContextHolderUtils;
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
    public ResponseEntity<?> startRecord(@RequestBody RecordReqDto.ReqStartRecord req){
        UUID recordId = null;
        UUID userId = SecurityContextHolderUtils.getUserId();

        try {
            recordId = recordService.startRecording(req, userId);
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
                .data(new RecordResDto.ResStartRecord(recordId))
                .message("success")
                .memo("기록 측정이 시작되었습니다. 반드시 종료를 해주세요.")
                .build();
        return new ResponseEntity<>(message, message.getStatus());
    }

    @PatchMapping("/one")
    public ResponseEntity<?> endRecord(@RequestBody RecordReqDto.ReqEndRecord req){

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
                .memo("측정 종료")
                .build();
        return new ResponseEntity<>(message, message.getStatus());
    }

    @GetMapping("/list")
    public ResponseEntity<?> searchListByUser(){
        UUID userId = SecurityContextHolderUtils.getUserId();
        RecordResDto.ResSearchListByUser resSearchListByUser = null;
        try {
            resSearchListByUser = recordService.findListByUser(userId);
        } catch (Exception e) {
            Message message = Message.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message("error")
                    .memo(e.getMessage())
                    .build();
            return new ResponseEntity<>(message, message.getStatus());
        }

        Message message = Message.builder()
                .data(resSearchListByUser)
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
