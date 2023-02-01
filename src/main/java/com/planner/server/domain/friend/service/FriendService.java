package com.planner.server.domain.friend.service;


import com.planner.server.domain.friend.dto.*;
import com.planner.server.domain.friend.entity.Friend;
import com.planner.server.domain.friend.repository.FriendRepository;
import com.planner.server.domain.user.dto.UserResDto;
import com.planner.server.domain.user.entity.User;
import com.planner.server.domain.user.repository.UserRepository;
import com.planner.server.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.Console;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FriendService {

    private final FriendRepository friendRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    Logger logger = LoggerFactory.getLogger(FriendService.class);

    public FriendResDto save(FriendReqDto req) throws Exception {

        List<UUID> list = List.of(req.getUserId(), req.getFriendId());
        List<User> users = new ArrayList<>();
        try{
            users = friendRepository.validateUser(list);
        } catch (Exception e){
            throw new Exception("[id] 확인 요망");
        }
        User user = users.get(1);
        User friend = users.get(0);

        validateUserAndFriend(user, friend);

        if(alreadyExists(user, friend)){
            throw new Exception("이미 전에 보냈던 친구 요청입니다.");
        }

        UUID id = UUID.randomUUID();
        Friend friendEntity = buildFriend(user, friend, id);

        Friend savedFriend = friendRepository.save(friendEntity);

        return FriendResDto.builder()
                .user(UserResDto.toDto(user))
                .friend(UserResDto.toDto(friend))
                .allow(false)
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Transactional
    public void reverseSave(User user, User friend) throws Exception {

        Optional<Friend> tmp = friendRepository.alreadyExists(user.getId(), friend.getId());
        if(tmp.isPresent()){
            Friend findFriend = tmp.get();
            System.out.println("findFriend.getId() = " + findFriend.getId());
            tmp.get().fixAllowance();
        }
        else {
            UUID id = UUID.randomUUID();
            Friend friendEntity = buildFriend(user, friend, id);
            friendEntity.fixAllowance();

            Friend savedFriend = friendRepository.save(friendEntity);
        }
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
        Optional<Friend> byId = friendRepository.findById(id);
        if(!byId.isPresent())
            throw new Exception("[id]확인 요망. 친구가 존재하지 않습니다.");
        return byId.get();
    }

    public FriendGetDto findByUserId(UUID id) throws Exception {
        List<Friend> friendList = new ArrayList<>();

        try{
            friendList = friendRepository.findByUserId(id);
        }
        catch (Exception e){
            throw new Exception("입력받은 유저의 id로 친구를 찾을 수 없습니다.");
        }
        List<FriendResDto> friendDtoList = new ArrayList<>();
        friendList.forEach(f -> friendDtoList.add(FriendResDto.toDto(f)));

        return FriendGetDto.toDto(friendDtoList);
    }

    public FriendGetDto findAll(){
        List<Friend> friendList = friendRepository.findAll();

        List<FriendResDto> friendDtoList = new ArrayList<>();
        friendList.stream().forEach(friend -> friendDtoList.add(FriendResDto.toDto(friend)));

        return FriendGetDto.toDto(friendDtoList);
    }

    public void deleteById(FriendReqDto req) throws Exception {
        logger.info("FriendService - deleteById 실행");
        Optional<Friend> findFriend = friendRepository.findByIdFetchJoin(req.getId());
        logger.info("FriendRepository - findById 실행 완료");

        if(!findFriend.isPresent()) throw new Exception("[id] 확인 요망. id에 해당하는 친구요청이 존재하지 않습니다.");

        Friend friend = findFriend.get();
        friendRepository.delete(friend);
        logger.info("FriendService - deleteById 실행 완료");
    }

    @Transactional
    public Friend changeAllowance(FriendReqDto req) throws Exception {
        UUID id = req.getId();
        Optional<Friend> friend = friendRepository.findById(id);
        logger.info("FriendRepository - findById 실행 완료");
        if(!friend.isPresent()){
            throw new Exception("[id]확인 요망. 해당 id의 친구요청이 존재하지 않습니다.");
        }
        friend.get().fixAllowance();
        return friend.get();
    }

    public void deleteByFriendId(UUID friendId){
        List<Friend> friendList = friendRepository.findByFriendId(UUID.fromString(friendId.toString()));

        friendList.stream().forEach(friend -> friendRepository.delete(friend));
    }
}
