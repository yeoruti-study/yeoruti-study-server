package com.planner.server.domain.user.controller;

import com.planner.server.domain.friend.entity.Friend;
import com.planner.server.domain.friend.service.FriendService;
import com.planner.server.domain.message.Message;
import com.planner.server.domain.user.dto.*;
import com.planner.server.domain.user.entity.User;
import com.planner.server.domain.user.repository.UserRepository;
import com.planner.server.domain.user.service.UserService;
import com.planner.server.domain.user_study_subject.entity.UserStudySubject;
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
    private final UserRepository userRepository;
    private final FriendService friendService;

    /**
     * 회원가입 - 유저생성 */
    @PostMapping ("/one")
    public ResponseEntity<?> signUp(@RequestBody UserReqDto req){
        try {
            userService.signUp(req);
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

    /**
     * 전체 유저 조회 */
    @GetMapping("/all")
    public ResponseEntity<?> findAll(){
        UserGetDto result = userService.findAll();
        Message message = Message.builder()
                .data(result)
                .status(HttpStatus.OK)
                .message("success")
                .build();
        return new ResponseEntity<>(message, message.getStatus());
    }

    /**
     * id(UUID)로 조회 */
    @GetMapping("/one")
    public ResponseEntity<?> findOne(@RequestParam UUID id){
        UserResDto userResDto = null;

        try {
            userResDto = UserResDto.toDto(userService.findById(id));
        } catch (Exception e) {
            Message message = Message.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message(e.getMessage())
                    .build();
            return new ResponseEntity<>(message, message.getStatus());
        }

        Message message = Message.builder()
                .data(userResDto)
                .status(HttpStatus.OK)
                .message("success")
                .build();
        return new ResponseEntity<>(message, message.getStatus());
    }


    /**
     * 유저 프로필 수정 (수정 예정)*/
    @PutMapping ("/profile/one")
    public ResponseEntity<?> updateUserProfile(@RequestBody UserReqDto req){
        try{
            userService.changeProfile(req);
        }
        catch (IllegalArgumentException e){
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

    /**
     * 유저 삭제*/
    @DeleteMapping("/one")
    public ResponseEntity<?> deleteUser(@RequestBody UserReqDto req){
        friendService.deleteByFriendId(req.getId());
        try {
            userService.deleteUser(req);
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
