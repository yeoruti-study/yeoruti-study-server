package com.planner.server.domain.study_goal.controller;

import com.planner.server.domain.message.Message;
import com.planner.server.domain.study_goal.dto.DeleteReqDto;
import com.planner.server.domain.study_goal.dto.SaveReqDto;
import com.planner.server.domain.study_goal.dto.StudyGoalDto;
import com.planner.server.domain.study_goal.service.StudyGoalService;
import com.planner.server.validate.Validate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/study_goal")
public class StudyGoalController {

    private final StudyGoalService studyGoalService;

    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody SaveReqDto req){
        StudyGoalDto studyGoalDto = null;
        try {
            studyGoalDto = studyGoalService.save(req);
        } catch (Exception e) {
            Message message = Message.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message(e.getMessage())
                    .build();
            return new ResponseEntity<>(message, message.getStatus());
        }

        Message message = Message.builder()
                .data(studyGoalDto)
                .status(HttpStatus.OK)
                .message("success")
                .build();
        return new ResponseEntity<>(message, message.getStatus());
    }

    @GetMapping("")
    public ResponseEntity<?> getById(@RequestParam UUID id){
        StudyGoalDto studyGoalDto;
        try {
            Validate.validateId(id);
            studyGoalDto = studyGoalService.findById(id);
        } catch (Exception e) {
            Message message = Message.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message(e.getMessage())
                    .build();
            return new ResponseEntity<>(message, message.getStatus());
        }

        Message message = Message.builder()
                .data(studyGoalDto)
                .status(HttpStatus.OK)
                .message("success")
                .build();
        return new ResponseEntity<>(message, message.getStatus());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteById(@RequestBody DeleteReqDto id){
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
