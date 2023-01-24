package com.planner.server.domain.user.controller;

import com.planner.server.domain.friend.service.FriendService;
import com.planner.server.domain.message.Message;
import com.planner.server.domain.user.dto.*;
import com.planner.server.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final FriendService friendService;

    /**
     * 회원가입 - 유저생성 */
    @PostMapping ("")
    public ResponseEntity<?> signUp(@RequestBody SignUpReqDto req){

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
        UserListDto result = userService.findAll();
        Message message = Message.builder()
                .data(result)
                .status(HttpStatus.OK)
                .message("success")
                .build();
        return new ResponseEntity<>(message, message.getStatus());
    }

    /**
     * 유저 id(UUID)로 조회 */
    @GetMapping("/id")
    public ResponseEntity<?> findOne(@RequestParam UUID id){
        UserDto userDto = null;

        try {
            userDto = UserDto.toDto(userService.findById(id));
        } catch (Exception e) {
            Message message = Message.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message(e.getMessage())
                    .build();
            return new ResponseEntity<>(message, message.getStatus());
        }

        Message message = Message.builder()
                .data(userDto)
                .status(HttpStatus.OK)
                .message("success")
                .build();
        return new ResponseEntity<>(message, message.getStatus());
    }

//    /**
//     * 프로필 이름으로 조회 */
//    @GetMapping("/profile")
//    public ResponseEntity<?> findByProfileName(@RequestParam String profileName) {
//        UserDto userDto = userService.findByProfileName(profileName);
//        Message message = Message.builder()
//                .data(userDto)
//                .status(HttpStatus.OK)
//                .message("success")
//                .build();
//        return new ResponseEntity<>(message, message.getStatus());
//    }

    /**
     * 유저 프로필 수정 (수정 예정)*/
    @PutMapping ("/profile")
    public ResponseEntity<?> updateUserProfile(@RequestBody ProfileReqDto req){
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
    @DeleteMapping("")
    public ResponseEntity<?> deleteUser(@RequestBody DeleteReqDto req){
        friendService.deleteByFriendId(req.getId());
        userService.deleteUser(req);

        Message message = Message.builder()
                .status(HttpStatus.OK)
                .message("success")
                .build();
        return new ResponseEntity<>(message, message.getStatus());
    }
}
