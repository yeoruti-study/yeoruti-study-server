package com.planner.server.domain.user.controller;

import com.planner.server.domain.user.dto.*;
import com.planner.server.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    /**
     * 회원가입 - 유저생성 */
    @PostMapping ("")
    public SaveUserResDto signIn(@RequestBody SaveUserReqDto req){
        return userService.save(req);
    }

    /**
     * 전체 유저 조회 */
    @GetMapping("/allusers")
    public UserListDto findAll(){
        UserListDto result = userService.findAll();
        return result;
    }

    /**
     * id 로 조회 (수정예정) */
    @GetMapping("/userid")
    public UserDto findOne(@RequestParam UUID id){
        return UserDto.toDto(userService.findById(id));
    }

    /**
     * 프로필 이름으로 조회 */
    @GetMapping("/profile")
    public UserDto findByProfileName(@RequestParam String name) {
        return userService.findByProfileName(name);
    }

    /**
     * 유저 프로필 수정 (수정 예정)*/
    @PatchMapping("/profile")
    public String updateUserProfile(@RequestBody UpdateProfileReqDto req){
        if(userService.changeProfile(req) == true)
            return "SUCCESS";
        else return "FAIL";
    }

    /**
     * 유저 삭제*/
    @DeleteMapping("")
    public String deleteMapping(@RequestBody DeleteUserReqDto req){
        userService.deleteUser(req);
        return "SUCCESS";
    }
}
