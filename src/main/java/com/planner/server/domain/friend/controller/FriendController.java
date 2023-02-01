package com.planner.server.domain.friend.controller;

import com.planner.server.domain.friend.dto.*;
import com.planner.server.domain.friend.entity.Friend;
import com.planner.server.domain.friend.service.FriendService;
import com.planner.server.domain.message.Message;
import com.planner.server.domain.user.entity.User;
import com.planner.server.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    Logger logger = LoggerFactory.getLogger(FriendService.class);

    @PostMapping("/one")
    public ResponseEntity<?> save(@RequestBody FriendReqDto req){
        try {
            FriendResDto savedFriend = friendService.save(req);
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
    public ResponseEntity<?> getAll(){
        FriendGetDto friendGetDto = friendService.findAll();

        Message message = Message.builder()
                .data(friendGetDto)
                .status(HttpStatus.OK)
                .message("success")
                .build();
        return new ResponseEntity<>(message, message.getStatus());
    }

    @GetMapping("/user/list")
    public ResponseEntity<?> getByUserId(@RequestParam UUID id){
        FriendGetDto friendGetDto = null;
        try {
            friendGetDto = friendService.findByUserId(id);
        } catch (Exception e) {
            Message message = Message.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message(e.getMessage())
                    .build();
            return new ResponseEntity<>(message, message.getStatus());
        }

        Message message = Message.builder()
                .data(friendGetDto)
                .status(HttpStatus.OK)
                .message("success")
                .build();
        return new ResponseEntity<>(message, message.getStatus());
    }

    @GetMapping("/one")
    public ResponseEntity<?> getById(@RequestParam UUID id){
        Friend byId = null;
        try {
            byId = friendService.findById(id);
        } catch (Exception e) {
            Message message = Message.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message(e.getMessage())
                    .build();
            return new ResponseEntity<>(message, message.getStatus());
        }
        Message message = Message.builder()
                .data(byId)
                .status(HttpStatus.OK)
                .message("success")
                .build();
        return new ResponseEntity<>(message, message.getStatus());
    }

    @PatchMapping("/one")
    // 한 번 사용하면 다시 못 사용하도록 추후에 조치하기(allow : false -> true 면 거기서 끝!)
    public ResponseEntity<?> changeAllowance(@RequestBody FriendReqDto req){
        Friend friendEntity = null;
        try {
            friendEntity = friendService.changeAllowance(req);
            logger.info("changeAllowance 완료");
        } catch (Exception e) {
            Message message = Message.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message(e.getMessage())
                    .build();
            return new ResponseEntity<>(message, message.getStatus());
        }

        User user = friendEntity.getFriend();
        System.out.println("user.getId() = " + user.getId());
        User friend = friendEntity.getUser();
        System.out.println("friend.getId() = " + friend.getId());

        logger.info("friendService.reverseSave 시작");
        try {
            friendService.reverseSave(user, friend);
        } catch (Exception e) {
            Message message = Message.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message(e.getMessage())
                    .build();
            return new ResponseEntity<>(message, message.getStatus());
        }

        logger.info("friendService.save 끝");
        user.addFriend(friendEntity);
        friend.addFriend(friendEntity);

        Message message = Message.builder()
                .status(HttpStatus.OK)
                .message("success")
                .memo("친구 요청이 수락되었습니다.")
                .build();
        return new ResponseEntity<>(message, message.getStatus());
    }

    @DeleteMapping("/one")
    public ResponseEntity<?> delete(@RequestBody FriendReqDto req){
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
