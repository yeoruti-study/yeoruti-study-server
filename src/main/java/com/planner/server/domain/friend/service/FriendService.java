package com.planner.server.domain.friend.service;


import com.planner.server.domain.friend.dto.*;
import com.planner.server.domain.friend.entity.Friend;
import com.planner.server.domain.friend.repository.FriendRepository;
import com.planner.server.domain.user.dto.UserDto;
import com.planner.server.domain.user.entity.User;
import com.planner.server.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FriendService {

    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    public FriendDto save(SaveFriendReqDto req){

        User user = userRepository.findByProfileName(req.getUserProfileName());
        User friend = userRepository.findByProfileName(req.getFriendProfileName());

        if(!user.isFriendAcceptance() || !friend.isFriendAcceptance())
            throw new IllegalArgumentException();
        if(user == friend)
            throw new IllegalArgumentException();

        UUID id = UUID.randomUUID();

        Friend friendEntity = Friend.builder()
                .id(id)
                .user(user)
                .friend(friend)
                .allow(false)
                .createdAt(LocalDateTime.now())
                .build();

        Friend savedFriend = friendRepository.save(friendEntity);


        return FriendDto.builder()
                .user(UserDto.toDto(user))
                .friend(UserDto.toDto(friend))
                .allow(false)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public Friend findById(String sid){
        UUID id = UUID.fromString(sid);
        Friend friend = friendRepository.findById(id).get();
        return friend;
    }

    public FindFriendByUserResDto findFriend(String userId){
        UUID id = UUID.fromString(userId);
        String profileName = userRepository.findById(id).get().getProfileName();

        List<Friend> friendList = friendRepository.findByUserId(id);

        List<FriendDto> friendDtoList = new ArrayList<>();
        friendList.stream().forEach(friend -> friendDtoList.add(FriendDto.toDto(friend)));
        return FindFriendByUserResDto.toDto(profileName, friendDtoList);
    }

    public FriendListDto findAll(){
        List<Friend> friendList = friendRepository.findAll();

        List<FriendDto> friendDtoList = new ArrayList<>();
        friendList.stream().forEach(friend -> friendDtoList.add(FriendDto.toDto(friend)));

        return FriendListDto.toDto(friendDtoList);
    }

    public String deleteFriend(DeleteFriendByUserReqDto req){
        Optional<Friend> findFriend = friendRepository.findById(UUID.fromString(req.getId()));

        if(!findFriend.isPresent()) throw new NullPointerException();

        Friend friend = findFriend.get();
        friendRepository.delete(friend);
        return "SUCCESS";
    }

    @Transactional
    public void changeAllowance(AllowanceReqDto req){
        UUID id = UUID.fromString(req.getId());
        Friend friend = friendRepository.findById(id).get();
        friend.fixAllowance();
    }
}
