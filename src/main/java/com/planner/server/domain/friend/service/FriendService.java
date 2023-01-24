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

    public FriendDto save(FriendReqDto req, int flag) throws Exception {

        Optional<User> findUser = userRepository.findByProfileName(req.getUserProfileName());
        Optional<User> findFriend = userRepository.findByProfileName(req.getFriendProfileName());
        if(!findUser.isPresent() || !findFriend.isPresent())
            throw new Exception("자신 또는 친구가 존재하지 않습니다. id 값을 확인해주세요.");

        User user = findUser.get();
        User friend = findFriend.get();

        if(flag == 1){
            Optional<Friend> tmp = friendRepository.alreadyExists(user.getId(), friend.getId());
            if(tmp.isPresent()){
                friendRepository.delete(tmp.get());
            }
        }

        if(alreadyExists(user, friend)){
            throw new Exception("이미 전에 보냈던 친구 요청입니다.");
        }

        validateUserAndFriend(user, friend);

        UUID id = UUID.randomUUID();
        Friend friendEntity = buildFriend(user, friend, id);

        if(flag == 1){
            friendEntity.fixAllowance();
            alreadyExists(user, friend);
        }

        Friend savedFriend = friendRepository.save(friendEntity);

        return FriendDto.builder()
                .user(UserDto.toDto(user))
                .friend(UserDto.toDto(friend))
                .allow(false)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public boolean alreadyExists(User user, User friend){
        UUID userId = user.getId();
        UUID friendId = friend.getId();
        Optional<Friend> findFriend = friendRepository.alreadyExists(userId, friendId);

        if(findFriend.isPresent())
            return true;

        return false;
    }

    private static Friend buildFriend(User user, User friend, UUID id) {
        Friend friendEntity = Friend.builder()
                .id(id)
                .user(user)
                .friend(friend)
                .allow(false)
                .createdAt(LocalDateTime.now())
                .build();
        return friendEntity;
    }

    private static void validateUserAndFriend(User user, User friend) throws Exception {
        if(!user.isFriendAcceptance())
            throw new Exception("자신의 \"친구수락 여부\"를 \"허용\"으로 바꿔주세요");
        if(!friend.isFriendAcceptance())
            throw new Exception("상대방이 친수수락 여부를 \"거부\"로 설정되어 있어 친구요청을 보낼 수 없습니다.");
        if(user == friend)
            throw new IllegalArgumentException("자기 자신에게는 요청을 보낼 수 없습니다.");
    }

    public Friend findById(UUID id) throws Exception {
        Friend friend = null;
        try{friend = friendRepository.findById(id).get();}
        catch (Exception e){
            throw new Exception("입력받은 ID로 친구를 찾을 수 없습니다.");
        }
        return friend;
    }

    public FriendListDto findByUserId(UUID id) throws Exception {
        List<Friend> friendList = new ArrayList<>();

        try{
            friendList = friendRepository.findByUserId(id);
        }
        catch (Exception e){
            throw new Exception("입력받은 유저의 id로 친구를 찾을 수 없습니다.");
        }
        List<FriendDto> friendDtoList = new ArrayList<>();
        friendList.forEach(f -> friendDtoList.add(FriendDto.toDto(f)));

        return FriendListDto.toDto(friendDtoList);
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

    public void deleteById(FriendDeleteReqDto req) throws Exception {
        Optional<Friend> findFriend = friendRepository.findById(UUID.fromString(req.getId()));

        if(!findFriend.isPresent()) throw new Exception("친구가 존재하지 않습니다.");

        Friend friend = findFriend.get();
        friendRepository.delete(friend);
    }

    public void deleteByFriendId(UUID friendId){
        List<Friend> friendList = friendRepository.findByFriendId(UUID.fromString(friendId.toString()));

        friendList.stream().forEach(friend -> friendRepository.delete(friend));
    }

    @Transactional
    public void changeAllowance(AllowanceReqDto req){
        UUID id = req.getId();
        Friend friend = friendRepository.findById(id).get();
        friend.fixAllowance();
    }

}
