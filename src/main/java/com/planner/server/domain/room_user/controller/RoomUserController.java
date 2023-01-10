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
import com.planner.server.domain.room_user.dto.RoomUserDto;
import com.planner.server.domain.room_user.service.RoomUserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/room-user")
public class RoomUserController {
    private final RoomUserService roomUserService;

    @PostMapping("/one")
    public ResponseEntity<?> createOne(@RequestBody RoomUserDto roomUserDto) {
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

    // @GetMapping("/list/user/{userId}")
    // public ResponseEntity<?> searchListByUserId(@PathVariable(value = "userId") UUID userId) {
    //     Message message = new Message();

    //     try {
    //         message.setData(roomUserService.searchListByUserId(userId));
    //         message.setStatus(HttpStatus.OK);
    //         message.setMessage("success");
    //     } catch (Exception e) {
    //         message.setStatus(HttpStatus.BAD_REQUEST);
    //         message.setMessage("error");
    //         message.setMemo(e.getMessage());
    //     }
    //     return new ResponseEntity<>(message, message.getStatus());
    // }

    @GetMapping("/list/study-room/{studyRoomId}")
    public ResponseEntity<?> searchListByStudyRoomId(@PathVariable(value = "studyRoomId") UUID studyRoomId) {
        Message message = new Message();

        try {
            message.setData(roomUserService.searchListByStudyRoomId(studyRoomId));
            message.setStatus(HttpStatus.OK);
            message.setMessage("success");
        } catch (Exception e) {
            message.setStatus(HttpStatus.BAD_REQUEST);
            message.setMessage("error");
            message.setMemo(e.getMessage());
        }
        return new ResponseEntity<>(message, message.getStatus());
    }

    @DeleteMapping("/one/{roomUserId}")
    public ResponseEntity<?> deleteOne(@PathVariable(name = "roomUserId") UUID roomUserId) {
        Message message = new Message();

        try {
            roomUserService.deleteOne(roomUserId);
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
