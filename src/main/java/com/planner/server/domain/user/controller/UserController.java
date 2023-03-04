package com.planner.server.domain.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.planner.server.domain.message.Message;
import com.planner.server.domain.user.dto.*;
import com.planner.server.domain.user.entity.User;
import com.planner.server.domain.user.service.UserService;
import com.planner.server.utils.SecurityContextHolderUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping ("/one")
    public ResponseEntity<?> createOne(@RequestBody UserReqDto.ReqCreateOne req){
        try {
            userService.createOne(req);
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

    @GetMapping("/one/{userId}")
    public ResponseEntity<?> searchOne(@PathVariable("userId") UUID id){
        try {
            UserResDto userResDto = UserResDto.toDto(userService.findOne(id));
            Message message = Message.builder()
                    .data(userResDto)
                    .status(HttpStatus.OK)
                    .message("success")
                    .build();
            return new ResponseEntity<>(message, message.getStatus());

        }catch (Exception e) {
            Message message = Message.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message("searching_error")
                    .message(e.getMessage())
                    .build();
            return new ResponseEntity<>(message, message.getStatus());
        }
    }

    @GetMapping("/one")
    public ResponseEntity<?> getCurrentUserInfo(){
        UUID userId = SecurityContextHolderUtils.getUserId();
        try {
            UserResDto userResDto = UserResDto.toDto(userService.findOne(userId));
            Message message = Message.builder()
                    .data(userResDto)
                    .status(HttpStatus.OK)
                    .message("success")
                    .build();
            return new ResponseEntity<>(message, message.getStatus());
        }catch (Exception e) {
            Message message = Message.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message("searching_error")
                    .message(e.getMessage())
                    .build();
            return new ResponseEntity<>(message, message.getStatus());
        }
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
    public ResponseEntity<?> updateProfile(@RequestBody UserReqDto.ReqUpdateProfile req){
        try{
            userService.changeUserInfo(req);
            Message message = Message.builder()
                    .status(HttpStatus.OK)
                    .message("success")
                    .build();
            return new ResponseEntity<>(message, message.getStatus());

        } catch (IllegalArgumentException e){
            Message message = Message.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message("error")
                    .memo(e.getMessage())
                    .build();
            return new ResponseEntity<>(message, message.getStatus());
        }
    }


    @DeleteMapping("/one")
    public ResponseEntity<?> deleteOne(@RequestBody UserReqDto.ReqDeleteUser req, HttpServletResponse response) throws Exception {
        Message message = new Message();
        try {
            userService.deleteUser(req);
        } catch (Exception e) {
            message = Message.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message("error")
                    .memo(e.getMessage())
                    .build();
            return new ResponseEntity<>(message, message.getStatus());
        }
        message = Message.builder()
                .status(HttpStatus.OK)
                .message("success")
                .memo("회원 탈퇴가 완료되었습니다.")
                .build();

        //쿠키 제거
        ObjectMapper om = new ObjectMapper();
        ResponseCookie cookies = ResponseCookie.from("yeoruti_token", null)
                .maxAge(0)     // 3일
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookies.toString());

        return new ResponseEntity<>(message, message.getStatus());
    }

}
