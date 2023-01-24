package com.planner.server.domain.friend.controller;

import com.planner.server.domain.friend.dto.*;
import com.planner.server.domain.friend.entity.Friend;
import com.planner.server.domain.friend.service.FriendService;
import com.planner.server.domain.message.Message;
import com.planner.server.domain.user.entity.User;
import com.planner.server.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/friend")
public class FriendController {

    private final FriendService friendService;
    private final UserService userService;

    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody FriendReqDto req){
        try {
            FriendDto savedFriend = friendService.save(req, 0);
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

    @GetMapping("/all")
    public ResponseEntity<?> getAllFriends(){
        FriendListDto friendListDto = friendService.findAll();

        Message message = Message.builder()
                .data(friendListDto)
                .status(HttpStatus.OK)
                .message("success")
                .build();
        return new ResponseEntity<>(message, message.getStatus());
    }

    @GetMapping("/user")
    public ResponseEntity<?> getFriendsByUserId(@RequestParam UUID id){
        FriendListDto friendListDto = null;
        try {
            friendListDto = friendService.findByUserId(id);
        } catch (Exception e) {
            Message message = Message.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message(e.getMessage())
                    .build();
            return new ResponseEntity<>(message, message.getStatus());
        }

        Message message = Message.builder()
                .data(friendListDto)
                .status(HttpStatus.OK)
                .message("success")
                .build();
        return new ResponseEntity<>(message, message.getStatus());
    }

    @PatchMapping("")
    // 한 번 사용하면 다시 못 사용하도록 추후에 조치하기(allow : false -> true 면 거기서 끝!)
    public ResponseEntity<?> changeAllowance(@RequestBody AllowanceReqDto req){
        friendService.changeAllowance(req);

        Friend friendEntity = null;
        try {
            friendEntity = friendService.findById(req.getId());
        } catch (Exception e) {
            Message message = Message.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message(e.getMessage())
                    .build();
            return new ResponseEntity<>(message, message.getStatus());
        }

        User user = friendEntity.getUser();
        User friend = friendEntity.getFriend();

        FriendReqDto reqDto = FriendReqDto.builder()
                .userProfileName(friend.getProfileName())
                .friendProfileName(user.getProfileName())
                .build();

        try {
            FriendDto save = friendService.save(reqDto, 1);
        } catch (Exception e) {
            Message message = Message.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message(e.getMessage())
                    .build();
            return new ResponseEntity<>(message, message.getStatus());
        }

        user.addFriend(friendEntity);
        friend.addFriend(friendEntity);

        Message message = Message.builder()
                .status(HttpStatus.OK)
                .message("success")
                .memo("친구 요청이 수락되었습니다.")
                .build();
        return new ResponseEntity<>(message, message.getStatus());
    }

    @DeleteMapping("")
    public ResponseEntity<?> deleteFriend(@RequestBody FriendDeleteReqDto req){
        try {
            friendService.deleteById(req);
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