package com.planner.server.domain.room_user.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.planner.server.domain.message.Message;
import com.planner.server.domain.room_user.dto.RoomUserReqDto;
import com.planner.server.domain.room_user.service.RoomUserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/room-user")
public class RoomUserController {
    private final RoomUserService roomUserService;

    @PostMapping("/one")
    public ResponseEntity<?> createOne(@RequestBody RoomUserReqDto roomUserDto) {
        Message message = new Message();

        try {
            roomUserService.createOne(roomUserDto);
            message.setStatus(HttpStatus.OK);
            message.setMessage("success");
        } catch (Exception e) {
            message.setStatus(HttpStatus.BAD_REQUEST);
            message.setMessage("error");
            message.setMemo(e.getMessage());
        }
        return new ResponseEntity<>(message, message.getStatus());
    }

    @DeleteMapping("/one/study-room/{studyRoomId}")
    public ResponseEntity<?> deleteOne(@PathVariable(name = "studyRoomId") UUID studyRoomId) {
        Message message = new Message();

        try {
            roomUserService.deleteOne(studyRoomId);
            message.setStatus(HttpStatus.OK);
            message.setMessage("success");
        } catch (Exception e) {
            message.setStatus(HttpStatus.BAD_REQUEST);
            message.setMessage("error");
            message.setMemo(e.getMessage());
        }
        return new ResponseEntity<>(message, message.getStatus());
    }

    // 스터디룸에 속한 유저 전체 조회
    @GetMapping("/user/list/study-room/{studyRoomId}")
    public ResponseEntity<?> searchUserListByStudyRoomId(@PathVariable(value = "studyRoomId") UUID studyRoomId) {
        Message message = new Message();

        try {
            message.setData(roomUserService.searchUserListByStudyRoomId(studyRoomId));
            message.setStatus(HttpStatus.OK);
            message.setMessage("success");
        } catch (Exception e) {
            message.setStatus(HttpStatus.BAD_REQUEST);
            message.setMessage("error");
            message.setMemo(e.getMessage());
        }
        return new ResponseEntity<>(message, message.getStatus());
    }

    // 유저가 속한 스터디룸 전체 조회
    @GetMapping("/study-room/list")
    public ResponseEntity<?> searchListJoinedStudyRoom() {
        Message message = new Message();

        try {
            message.setData(roomUserService.searchListJoinedStudyRoom());
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
