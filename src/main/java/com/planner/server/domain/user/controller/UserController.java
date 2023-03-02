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
                    .message("searching_error")
                    .message(e.getMessage())
                    .build();
            return new ResponseEntity<>(message, message.getStatus());
        }

        return new ResponseEntity<>(message, message.getStatus());
    }

    @GetMapping("/one")
    public ResponseEntity<?> getCurrentUserInfo(){
        UserResDto userResDto = null;
        Message message = new Message();

        UUID userId = SecurityContextHolderUtils.getUserId();

        try {
            userResDto = UserResDto.toDto(userService.findOne(userId));
            message = Message.builder()
                    .data(userResDto)
                    .status(HttpStatus.OK)
                    .message("success")
                    .build();

        }catch (Exception e) {
            message = Message.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message("searching_error")
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
    public ResponseEntity<?> updateProfile(@RequestBody UserReqDto.ReqUpdateProfile req){
        Message message = new Message();
        UUID userId = SecurityContextHolderUtils.getUserId();

        try{
            userService.changeUserInfo(req, userId);
            message = Message.builder()
                    .status(HttpStatus.OK)
                    .message("success")
                    .build();
        } catch (IllegalArgumentException e){
            message = Message.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message("error")
                    .memo(e.getMessage())
                    .build();
        }
        return new ResponseEntity<>(message, message.getStatus());
    }


    @DeleteMapping("/one")
    public ResponseEntity<?> deleteOne(@RequestBody UserReqDto.ReqDeleteUser req, HttpServletResponse response) throws Exception {
        Message message = new Message();

        String username = SecurityContextHolderUtils.getUsername();
        if(!username.equals(req.getUsername())){
            message.setStatus(HttpStatus.BAD_REQUEST);
            message.setMessage("error");
            message.setMemo("username 재확인 요망.");
            return new ResponseEntity<>(message, message.getStatus());
        }
        UUID userId = SecurityContextHolderUtils.getUserId();
        User findUser = userService.findOne(userId);

        if(!userService.checkPassword(findUser, req.getPassword())){
            message = Message.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message("error")
                    .memo("username, password 확인 요망")
                    .build();
            return new ResponseEntity<>(message, message.getStatus());

        }
        try {
            userService.deleteOne(findUser);
        } catch (Exception e) {
            message = Message.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message("error")
                    .memo(e.getMessage())
                    .build();
        }
        message = Message.builder()
                .status(HttpStatus.OK)
                .message("success")
                .build();


        //쿠키 제거
        ObjectMapper om = new ObjectMapper();
        ResponseCookie cookies = ResponseCookie.from("yeoruti_token", null)
                .httpOnly(true)
                .domain("localhost")
                .sameSite("Strict")
                .path("/")
                .maxAge(0)     // 3일
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookies.toString());

        return new ResponseEntity<>(message, message.getStatus());
    }

}
