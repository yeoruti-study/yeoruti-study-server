package com.planner.server.domain.record.controller;

import com.planner.server.domain.friend.dto.FriendDto;
import com.planner.server.domain.friend.dto.SaveReqDto;
import com.planner.server.domain.message.Message;
import com.planner.server.domain.record.dto.RecordReqDto;
import com.planner.server.domain.record.dto.RecordResDto;
import com.planner.server.domain.record.service.RecordService;
import com.planner.server.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/record")
public class RecordController {

    private final RecordService recordService;
    private final UserService userService;

    Logger logger = LoggerFactory.getLogger(RecordService.class);

    @PostMapping("/one")
    public ResponseEntity<?> save(@RequestBody RecordReqDto req){
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

    @GetMapping("/all")
    public ResponseEntity<?> getAll(){
        List<RecordResDto> recordResDtoList = recordService.getAll();

        Message message = Message.builder()
                .data(recordResDtoList)
                .status(HttpStatus.OK)
                .message("success")
                .build();
        return new ResponseEntity<>(message, message.getStatus());
    }

    @GetMapping("/user/list")
    public ResponseEntity<?> getByUserId(@RequestParam("userId") UUID req){
        List<RecordResDto> recordResDtoList = null;
        try {
            recordResDtoList = recordService.getByUserId(req);
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
    public ResponseEntity<?> getByUserStudySubjectId(@RequestParam("id") UUID req){
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
    public ResponseEntity<?> delete(@RequestBody RecordReqDto req){
        try {
            recordService.deleteById(req);
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
