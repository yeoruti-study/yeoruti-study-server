package com.planner.server.domain.friend.controller;

import com.planner.server.domain.friend.dto.*;
import com.planner.server.domain.friend.entity.Friend;
import com.planner.server.domain.friend.service.FriendService;
import com.planner.server.domain.user.entity.User;
import com.planner.server.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/friend")
public class FriendController {

    private final FriendService friendService;
    private final UserService userService;

    @PostMapping("")
    public FriendDto save(@RequestBody SaveFriendReqDto req){
        FriendDto savedFriend = friendService.save(req);
        return savedFriend;
    }

    @GetMapping("")
    public FriendListDto getAllFriends(){
        return friendService.findAll();
    }

    @GetMapping("/user")
    public FindFriendByUserResDto getFriendsByUser(@RequestParam String id){
        return friendService.findFriend(id);
    }

    @PatchMapping("")
    public String changeAllowance(@RequestBody AllowanceReqDto req){
        friendService.changeAllowance(req);
        Friend friendEntity = friendService.findById(req.getId());
        
        User user = friendEntity.getUser();
        User friend = friendEntity.getFriend();

        user.addFriend(friendEntity);
        friend.addFriend(friendEntity);

        return "SUCCESS";
    }

    @DeleteMapping("")
    public String deleteFriend(@RequestBody DeleteFriendByUserReqDto req){
        return friendService.deleteFriend(req);
    }

}
