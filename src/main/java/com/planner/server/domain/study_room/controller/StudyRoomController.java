package com.planner.server.domain.study_room.controller;

import java.util.UUID;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.planner.server.domain.message.Message;
import com.planner.server.domain.study_room.dto.StudyRoomReqDto;
import com.planner.server.domain.study_room.service.StudyRoomService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/study-room")
@RequiredArgsConstructor
public class StudyRoomController {
    private final StudyRoomService studyRoomService;

    @PostMapping("/one")
    public ResponseEntity<?> createOne(@RequestBody StudyRoomReqDto.JoinStudyCategory studyRoomDto) {
        Message message = new Message();

        try {
            studyRoomService.createOne(studyRoomDto);
            // TODO :: 스터디룸 생성하면서 room user도 함께 생성하기
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
            message.setData(studyRoomService.searchAll());
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
    public ResponseEntity<?> updateOne(@RequestBody StudyRoomReqDto.JoinStudyCategory studyRoomDto) {
        Message message = new Message();

        try {
            studyRoomService.updateOne(studyRoomDto);
            message.setStatus(HttpStatus.OK);
            message.setMessage("success");
        } catch (Exception e) {
            message.setStatus(HttpStatus.BAD_REQUEST);
            message.setMessage("error");
            message.setMemo(e.getMessage());
        }
        return new ResponseEntity<>(message, message.getStatus());
    }

    @DeleteMapping("/one/{studyRoomId}")
    public ResponseEntity<?> deleteOne(@PathVariable(name = "studyRoomId") UUID studyRoomId) {
        Message message = new Message();

        try {
            studyRoomService.deleteOne(studyRoomId);
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

    // study category로 study room 조회 - 특정 카테고리의 스터디룸 확인
    @GetMapping("/study-category/{studyCategoryId}")
    public ResponseEntity<?> searchListByStudyCategory(@PathVariable(name = "studyCategoryId") UUID studyCategoryId) {
        Message message = new Message();

        try {
            message.setData(studyRoomService.searchListByStudyCategory(studyCategoryId));
            message.setStatus(HttpStatus.OK);
            message.setMessage("success");
        } catch (Exception e) {
            message.setStatus(HttpStatus.BAD_REQUEST);
            message.setMessage("error");
            message.setMemo(e.getMessage());
        }
        return new ResponseEntity<>(message, message.getStatus());
    }

    // 스터디룸 비밀번호 수정
    @PatchMapping("/room-password")
    public ResponseEntity<?> changeRoomPassword(@RequestBody StudyRoomReqDto.ReqChangePassword studyRoomDto) {
        Message message = new Message();

        try {
            studyRoomService.changeRoomPassword(studyRoomDto);
            message.setStatus(HttpStatus.OK);
            message.setMessage("success");
        } catch (Exception e) {
            message.setStatus(HttpStatus.BAD_REQUEST);
            message.setMessage("error");
            message.setMemo(e.getMessage());
        }
        return new ResponseEntity<>(message, message.getStatus());
    }

    // 스터디룸 비밀번호 확인
    @PostMapping("/room-password/check")
    public ResponseEntity<?> checkRoomPassword(@RequestBody StudyRoomReqDto.ReqCheckRoomPassword studyRoomDto) {
        Message message = new Message();

        try {
            studyRoomService.checkRoomPassword(studyRoomDto);
            message.setStatus(HttpStatus.OK);
            message.setMessage("success");
        } catch (Exception e) {
            message.setStatus(HttpStatus.BAD_REQUEST);
            message.setMessage("error");
            message.setMemo(e.getMessage());
        }
        return new ResponseEntity<>(message, message.getStatus());
    }
}
