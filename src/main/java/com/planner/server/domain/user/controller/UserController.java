package com.planner.server.domain.user.controller;

import com.planner.server.domain.friend.service.FriendService;
import com.planner.server.domain.message.Message;
import com.planner.server.domain.user.dto.*;
import com.planner.server.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final FriendService friendService;

    @PostMapping ("/one")
    public ResponseEntity<?> createOne(@RequestBody UserReqDto req){
        Message message = new Message();
        try {
            userService.createOne(req);
            message.setStatus(HttpStatus.OK);
        } catch (Exception e) {
            message = Message.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message(e.getMessage())
                    .build();
            return new ResponseEntity<>(message, message.getStatus());
        }
        message = Message.builder()
                .status(HttpStatus.OK)
                .message("success")
                .build();

        return new ResponseEntity<>(message, message.getStatus());
    }

    @GetMapping("/one/{userId}")
    public ResponseEntity<?> searchOne(@PathVariable("userId") UUID id){
        UserResDto userResDto = null;
        Message message = new Message();
        try {
            userResDto = UserResDto.toDto(userService.findOne(id));
            message = Message.builder()
                    .data(userResDto)
                    .status(HttpStatus.OK)
                    .message("success")
                    .build();

        }catch (Exception e) {
            message = Message.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message(e.getMessage())
                    .build();
            return new ResponseEntity<>(message, message.getStatus());
        }

        return new ResponseEntity<>(message, message.getStatus());
    }

    @GetMapping("/all")
    public ResponseEntity<?> searchAll(){
        List<UserResDto> result = userService.findAll();

        Message message = Message.builder()
                .data(result)
                .status(HttpStatus.OK)
                .message("success")
                .build();
        return new ResponseEntity<>(message, message.getStatus());
    }

    @PutMapping ("/profile/one")
    public ResponseEntity<?> updateProfile(@RequestBody UserReqDto req){
        Message message = new Message();

        try{
            userService.changeUserInfo(req);
            message = Message.builder()
                    .status(HttpStatus.OK)
                    .message("success")
                    .build();
        } catch (IllegalArgumentException e){
            message = Message.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message(e.getMessage())
                    .build();
        }
        return new ResponseEntity<>(message, message.getStatus());
    }


    @DeleteMapping("/one/{userId}")
    public ResponseEntity<?> deleteOne(@PathVariable("userId") UUID id){
        friendService.deleteByFriendId(id);
        try {
            userService.deleteOne(id);
        } catch (Exception e) {
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
