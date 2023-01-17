package com.planner.server.domain.study_room.controller;

import java.util.UUID;

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
import com.planner.server.domain.study_room.dto.StudyRoomDto;
import com.planner.server.domain.study_room.service.StudyRoomService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/study-room")
@RequiredArgsConstructor
public class StudyRoomController {
    private final StudyRoomService studyRoomService;

    @PostMapping("/one")
    public ResponseEntity<?> createOne(@RequestBody StudyRoomDto studyRoomDto) {
        Message message = new Message();

        try {
            studyRoomService.createOne(studyRoomDto);
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
    public ResponseEntity<?> updateOne(@RequestBody StudyRoomDto studyRoomDto) {
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
        } catch (Exception e) {
            message.setStatus(HttpStatus.BAD_REQUEST);
            message.setMessage("error");
            message.setMemo(e.getMessage());
        }
        return new ResponseEntity<>(message, message.getStatus());
    }

    @GetMapping("/room-chat/{studyRoomId}")
    public ResponseEntity<?> searchStudyRoomChat(@PathVariable(name = "studyRoomId") UUID studyRoomId) {
        Message message = new Message();

        try {
            message.setData(studyRoomService.searchStudyRoomChat(studyRoomId));
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
