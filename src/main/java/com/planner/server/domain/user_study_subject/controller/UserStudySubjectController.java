package com.planner.server.domain.user_study_subject.controller;

import com.planner.server.domain.message.Message;
import com.planner.server.domain.user_study_subject.dto.DeleteReqDto;
import com.planner.server.domain.user_study_subject.dto.SaveReqDto;
import com.planner.server.domain.user_study_subject.dto.UserSubjectDto;
import com.planner.server.domain.user_study_subject.dto.UserSubjectListDto;
import com.planner.server.domain.user_study_subject.service.UserStudySubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user-study-subject")
public class UserStudySubjectController {

    private final UserStudySubjectService userStudySubjectService;

    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody SaveReqDto req){
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

    @GetMapping("/user")
    public ResponseEntity<?> findByUser(@RequestParam UUID id){
        UserSubjectListDto userSubjectListDto;
        try {
            userSubjectListDto = userStudySubjectService.findByUserId(id);
        }catch (Exception e) {
            Message message = Message.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message(e.getMessage())
                    .build();
            return new ResponseEntity<>(message, message.getStatus());
        }

        Message message = Message.builder()
                .data(userSubjectListDto)
                .status(HttpStatus.OK)
                .message("success")
                .build();
        return new ResponseEntity<>(message, message.getStatus());
    }


    @GetMapping("")
    public ResponseEntity<?> findById(@RequestParam UUID id){
        UserSubjectDto byId;
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

    @DeleteMapping("")
    public ResponseEntity<?> deleteById(@RequestBody DeleteReqDto req){
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

    @DeleteMapping("/user")
    public ResponseEntity<?> deleteByUserId(@RequestBody DeleteReqDto req){
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
                .status(HttpStatus.OK)
                .message("success")
                .build();
        return new ResponseEntity<>(message, message.getStatus());
    }

}
