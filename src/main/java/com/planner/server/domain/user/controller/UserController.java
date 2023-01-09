package com.planner.server.domain.user.controller;

import com.planner.server.domain.user.dto.SaveUserReqDto;
import com.planner.server.domain.user.dto.SaveUserResDto;
import com.planner.server.domain.user.dto.UserDto;
import com.planner.server.domain.user.dto.UserListDto;
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
    @PostMapping ("/save")
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
    @GetMapping("/profilename")
    public UserDto findByProfileName(@RequestParam String name) {
        return userService.findByProfileName(name);
    }
}
