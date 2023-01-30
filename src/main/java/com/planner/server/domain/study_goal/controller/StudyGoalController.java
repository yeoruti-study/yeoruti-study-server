package com.planner.server.domain.study_goal.controller;

import com.planner.server.domain.message.Message;
import com.planner.server.domain.study_goal.dto.StudyGoalReqDto;
import com.planner.server.domain.study_goal.dto.StudyGoalResDto;
import com.planner.server.domain.study_goal.service.StudyGoalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/study_goal")
public class StudyGoalController {

    private final StudyGoalService studyGoalService;

    @PostMapping("/one")
    public ResponseEntity<?> save(@RequestBody StudyGoalReqDto req){
        StudyGoalReqDto studyGoalReqDto = null;
        try {
            studyGoalReqDto = studyGoalService.save(req);
        } catch (Exception e) {
            Message message = Message.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message(e.getMessage())
                    .build();
            return new ResponseEntity<>(message, message.getStatus());
        }

        Message message = Message.builder()
                .data(studyGoalReqDto)
                .status(HttpStatus.OK)
                .message("success")
                .build();
        return new ResponseEntity<>(message, message.getStatus());
    }

    @GetMapping("/one")
    public ResponseEntity<?> getById(@RequestParam UUID id){
        StudyGoalResDto studyGoalResDto;
        try {
            studyGoalResDto = studyGoalService.findById(id);
        } catch (Exception e) {
            Message message = Message.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message(e.getMessage())
                    .build();
            return new ResponseEntity<>(message, message.getStatus());
        }

        Message message = Message.builder()
                .data(studyGoalResDto)
                .status(HttpStatus.OK)
                .message("success")
                .build();
        return new ResponseEntity<>(message, message.getStatus());
    }

    @GetMapping("/user/one")
    public ResponseEntity<?> getByUserId(@RequestParam UUID userId){
        List<StudyGoalResDto> studyGoalResDtos = new ArrayList<>();
        try {
            studyGoalResDtos = studyGoalService.findByUserId(userId);
        } catch (Exception e) {
            Message message = Message.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message(e.getMessage())
                    .build();
            return new ResponseEntity<>(message, message.getStatus());
        }

        Message message = Message.builder()
                .data(studyGoalResDtos)
                .status(HttpStatus.OK)
                .message("success")
                .build();
        return new ResponseEntity<>(message, message.getStatus());
    }

    @DeleteMapping("/delete/one")
    public ResponseEntity<?> deleteById(@RequestBody StudyGoalReqDto id){
        try {
            studyGoalService.deleteById(id);
        } catch (NoSuchElementException e) {
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
