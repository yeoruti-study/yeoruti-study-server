package com.planner.server.domain.user_study_subject.controller;

import com.planner.server.domain.message.Message;
import com.planner.server.domain.user_study_subject.dto.UserStudySubjectReqDto;
import com.planner.server.domain.user_study_subject.dto.UserStudySubjectResDto;
import com.planner.server.domain.user_study_subject.service.UserStudySubjectService;
import com.planner.server.utils.SecurityContextHolderUtils;
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

    @PostMapping("/one")
    public ResponseEntity<?> createOne(@RequestBody UserStudySubjectReqDto.ReqCreateOne req){
        UUID userId = SecurityContextHolderUtils.getUserId();
        try{
            userStudySubjectService.save(req, userId);
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

    @GetMapping("/list")
    public ResponseEntity<?> searchList(){
        UUID userId = SecurityContextHolderUtils.getUserId();
        UserStudySubjectResDto.ResSearchList searchList = new UserStudySubjectResDto.ResSearchList();
        try {
            searchList = userStudySubjectService.findByUserId(userId);
        }catch (Exception e) {
            Message message = Message.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message(e.getMessage())
                    .build();
            return new ResponseEntity<>(message, message.getStatus());
        }

        Message message = Message.builder()
                .data(searchList)
                .status(HttpStatus.OK)
                .message("success")
                .build();
        return new ResponseEntity<>(message, message.getStatus());
    }

    @GetMapping("/one/{userStudySubjectId}")
    public ResponseEntity<?> searchOne(@PathVariable("userStudySubjectId") UUID id){
        UserStudySubjectResDto userStudySubjectResDto;
        try {
            userStudySubjectResDto = userStudySubjectService.findById(id);
        } catch (Exception e) {
            Message message = Message.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message(e.getMessage())
                    .build();
            return new ResponseEntity<>(message, message.getStatus());
        }

        Message message = Message.builder()
                .data(userStudySubjectResDto)
                .status(HttpStatus.BAD_REQUEST)
                .message("success")
                .build();
        return new ResponseEntity<>(message, message.getStatus());
    }

    @DeleteMapping("/one/{userStudySubjectId}")
    public ResponseEntity<?> deleteOne(@PathVariable("userStudySubjectId") UUID id){
        try {
            userStudySubjectService.deleteById(id);
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
