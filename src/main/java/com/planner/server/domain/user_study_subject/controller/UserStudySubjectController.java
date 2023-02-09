package com.planner.server.domain.user_study_subject.controller;

import com.planner.server.domain.message.Message;
import com.planner.server.domain.user_study_subject.dto.UserStudySubjectReqDto;
import com.planner.server.domain.user_study_subject.dto.UserStudySubjectResDto;
import com.planner.server.domain.user_study_subject.service.UserStudySubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user-study-subject")
public class UserStudySubjectController {

    private final UserStudySubjectService userStudySubjectService;

    @PostMapping("/one")
    public ResponseEntity<?> save(@RequestBody UserStudySubjectReqDto req){
        try{
            userStudySubjectService.save(req);
        }catch (Exception e){
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

    @GetMapping("/user/one")
    public ResponseEntity<?> findByUserId(@RequestParam UUID userId){
        List<UserStudySubjectResDto> list = new ArrayList<>();
        try {
            list = userStudySubjectService.findByUserId(userId);
        }catch (Exception e) {
            Message message = Message.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message(e.getMessage())
                    .build();
            return new ResponseEntity<>(message, message.getStatus());
        }

        Message message = Message.builder()
                .data(list)
                .status(HttpStatus.OK)
                .message("success")
                .build();
        return new ResponseEntity<>(message, message.getStatus());
    }


    @GetMapping("/one")
    public ResponseEntity<?> findById(@RequestParam UUID id){
        UserStudySubjectResDto byId;
        try {
            byId = userStudySubjectService.findById(id);
        } catch (Exception e) {
            Message message = Message.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message(e.getMessage())
                    .build();
            return new ResponseEntity<>(message, message.getStatus());
        }

        Message message = Message.builder()
                .data(byId)
                .status(HttpStatus.BAD_REQUEST)
                .message("success")
                .build();
        return new ResponseEntity<>(message, message.getStatus());
    }

    @DeleteMapping("/one")
    public ResponseEntity<?> deleteById(@RequestBody UserStudySubjectReqDto req){
        try {
            userStudySubjectService.deleteById(req);
        }catch (Exception e) {
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

    @DeleteMapping("/user/one")
    public ResponseEntity<?> deleteByUserId(@RequestBody UserStudySubjectReqDto req){
        try {
            userStudySubjectService.deleteByUserId(req);
        }catch (Exception e) {
            Message message = Message.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message(e.getMessage())
                    .build();
            return new ResponseEntity<>(message, message.getStatus());
        }
        Message message = Message.builder()
                .message("success")
                .build();
        return new ResponseEntity<>(message, message.getStatus());
    }

}
