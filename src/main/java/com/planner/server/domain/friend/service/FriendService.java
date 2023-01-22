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

    public FriendDto save(SaveFriendReqDto req, int flag){

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

        if(flag == 1)
            friendEntity.fixAllowance();

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

    public FriendResDto findByUserId(String userId){
        UUID id = UUID.fromString(userId);
        String profileName = userRepository.findById(id).get().getProfileName();

        List<Friend> friendList = friendRepository.findByUserId(id);

        List<FriendDto> friendDtoList = new ArrayList<>();
        friendList.stream().forEach(friend -> friendDtoList.add(FriendDto.toDto(friend)));
        return FriendResDto.toDto(profileName, friendDtoList);
    }

    public List<Friend> findByFriendId(String friendId) {
        UUID id = UUID.fromString(friendId);
        return friendRepository.findByFriendId(id);
    }

    public FriendListDto findAll(){
        List<Friend> friendList = friendRepository.findAll();

        List<FriendDto> friendDtoList = new ArrayList<>();
        friendList.stream().forEach(friend -> friendDtoList.add(FriendDto.toDto(friend)));

        return FriendListDto.toDto(friendDtoList);
    }

    public String deleteById(DeleteFriendByUserReqDto req){
        Optional<Friend> findFriend = friendRepository.findById(UUID.fromString(req.getId()));

        if(!findFriend.isPresent()) throw new NullPointerException();

        Friend friend = findFriend.get();
        friendRepository.delete(friend);
        return "SUCCESS";
    }

    public void deleteByFriendId(UUID friendId){
        List<Friend> friendList = friendRepository.findByFriendId(UUID.fromString(friendId.toString()));

        friendList.stream().forEach(friend -> friendRepository.delete(friend));
    }

    @Transactional
    public void changeAllowance(AllowanceReqDto req){
        UUID id = UUID.fromString(req.getId());
        Friend friend = friendRepository.findById(id).get();
        friend.fixAllowance();
    }

}
