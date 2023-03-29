package com.planner.server.domain.friend.controller;

import com.planner.server.domain.friend.dto.*;
import com.planner.server.domain.friend.service.FriendService;
import com.planner.server.domain.message.Message;
import com.planner.server.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/friend")
public class FriendController {

    private final FriendService friendService;
    private final UserService userService;

    @PostMapping("/one")
    public ResponseEntity<?> createOne(@RequestBody FriendReqDto.ReqCreateOne req){
        try {
            friendService.createOne(req);
        } catch (Exception e) {
            Message message = Message.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message("error")
                    .memo(e.getMessage())
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
    public ResponseEntity<?> searchFriendList(){
        List<FriendResDto> friendResDtoList = new ArrayList<>();
        try {
            friendResDtoList = friendService.searchUserFriendList();
        } catch (Exception e) {
            Message message = Message.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message("error")
                    .memo(e.getMessage())
                    .build();
            return new ResponseEntity<>(message, message.getStatus());
        }
        Message message = Message.builder()
                .data(friendResDtoList)
                .status(HttpStatus.OK)
                .message("success")
                .build();
        return new ResponseEntity<>(message, message.getStatus());
    }

    @GetMapping("/received/list")
    public ResponseEntity<?> searchReceivedFriendRequestList(){ // 내가 받은 친구요청 목록 조회
        List<FriendResDto.FriendRequest> friendResDtoList = new ArrayList<>();
        try {
            friendResDtoList = friendService.searchReceiveFriendRequest();
        } catch (Exception e) {
            Message message = Message.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message("error")
                    .memo(e.getMessage())
                    .build();
            return new ResponseEntity<>(message, message.getStatus());
        }
        Message message = Message.builder()
                .data(friendResDtoList)
                .status(HttpStatus.OK)
                .message("success")
                .build();
        return new ResponseEntity<>(message, message.getStatus());
    }

    @GetMapping("/send/list")
    public ResponseEntity<?> searchSendFriendRequestList(){ // 내가 보낸 친구목록 조회
        List<FriendResDto.FriendRequest> friendResDtoList = new ArrayList<>();
        try {
            friendResDtoList = friendService.searchSendFriendRequest();
        } catch (Exception e) {
            Message message = Message.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message("error")
                    .memo(e.getMessage())
                    .build();
            return new ResponseEntity<>(message, message.getStatus());
        }
        Message message = Message.builder()
                .data(friendResDtoList)
                .status(HttpStatus.OK)
                .message("success")
                .build();
        return new ResponseEntity<>(message, message.getStatus());
    }

    @PatchMapping("/one")
    // 친구 요청 수락. 한 번 사용하면 다시 못 사용하도록 추후에 조치하기(allow : false -> true 면 거기서 끝!)
    public ResponseEntity<?> acceptFriendRequest(@RequestBody FriendReqDto.ReqAccept req){
        try {
            friendService.acceptFriendRequest(req);
        } catch (Exception e) {
            Message message = Message.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message("error")
                    .memo(e.getMessage())
                    .build();
            return new ResponseEntity<>(message, message.getStatus());
        }
        Message message = Message.builder()
                .status(HttpStatus.OK)
                .message("success")
                .memo("친구 요청을 수락했습니다.")
                .build();
        return new ResponseEntity<>(message, message.getStatus());
    }

    @DeleteMapping("/one")
    public ResponseEntity<?> deleteOne(@RequestBody FriendReqDto.ReqDeleteOne req){
        try {
            friendService.deleteOne(req);
        } catch (Exception e) {
            Message message = Message.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message("error")
                    .memo(e.getMessage())
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