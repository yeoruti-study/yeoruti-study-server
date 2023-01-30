package com.planner.server.domain.study_category.controller;

import java.util.UUID;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.planner.server.domain.message.Message;
import com.planner.server.domain.study_category.dto.StudyCategoryReqDto;
import com.planner.server.domain.study_category.service.StudyCategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/study-category")
public class StudyCategoryController {
    private final StudyCategoryService studyCategoryService;

    @PostMapping("/one")
    public ResponseEntity<?> createOne(@RequestBody StudyCategoryReqDto studyCategoryDto) {
        Message message = new Message();

        try {
            studyCategoryService.createOne(studyCategoryDto);
            message.setStatus(HttpStatus.OK);
            message.setMessage("success");
        } catch (Exception e) {
            message.setStatus(HttpStatus.BAD_REQUEST);
            message.setMessage("error");
            message.setMemo(e.getMessage());
        }
        return new ResponseEntity<>(message, message.getStatus());
    }

    @GetMapping("/all")
    public ResponseEntity<?> searchAll() {
        Message message = new Message();

        try {
            message.setData(studyCategoryService.searchAll());
            message.setStatus(HttpStatus.OK);
            message.setMessage("success");
        } catch (Exception e) {
            message.setStatus(HttpStatus.BAD_REQUEST);
            message.setMessage("error");
            message.setMemo(e.getMessage());
        }
        return new ResponseEntity<>(message, message.getStatus());
    }

    @PutMapping("/one")
    public ResponseEntity<?> updateOne(@RequestBody StudyCategoryReqDto studyCategoryDto) {
        Message message = new Message();

        try {
            studyCategoryService.updateOne(studyCategoryDto);
            message.setStatus(HttpStatus.OK);
            message.setMessage("success");
        } catch (Exception e) {
            message.setStatus(HttpStatus.BAD_REQUEST);
            message.setMessage("error");
            message.setMemo(e.getMessage());
        }
        return new ResponseEntity<>(message, message.getStatus());
    }

    @DeleteMapping("/one/{studyCategoryId}")
    public ResponseEntity<?> deleteOne(@PathVariable(name = "studyCategoryId") UUID studyCategoryId) {
        Message message = new Message();

        try {
            studyCategoryService.deleteOne(studyCategoryId);
            message.setStatus(HttpStatus.OK);
            message.setMessage("success");
        } catch(ConstraintViolationException e) {
            message.setStatus(HttpStatus.BAD_REQUEST);
            message.setMessage("error");
            message.setMemo("삭제할 수 없는 데이터입니다.");
        } catch (Exception e) {
            message.setStatus(HttpStatus.BAD_REQUEST);
            message.setMessage("error");
            message.setMemo(e.getMessage());
        }
        return new ResponseEntity<>(message, message.getStatus());
    }
}
