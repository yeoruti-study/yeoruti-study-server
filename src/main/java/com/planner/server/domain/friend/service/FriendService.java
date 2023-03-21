package com.planner.server.domain.friend.service;

import com.planner.server.domain.friend.dto.FriendReqDto;
import com.planner.server.domain.friend.dto.FriendResDto;
import com.planner.server.domain.friend.entity.Friend;
import com.planner.server.domain.friend.repository.FriendRepository;
import com.planner.server.domain.user.entity.User;
import com.planner.server.domain.user.repository.UserRepository;
import com.planner.server.utils.SecurityContextHolderUtils;
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

    public void createOne(FriendReqDto.ReqCreateOne req) throws Exception {
        UUID userId = SecurityContextHolderUtils.getUserId();
        Optional<User> findUser = userRepository.findById(userId);
        Optional<User> findFriendEntity = userRepository.findByUsername(req.getFriendUsername());

        if(!findUser.isPresent() || !findFriendEntity.isPresent()) {
            throw new IllegalArgumentException("id에 부합하는 유저가 존재하지 않습니다. 다시 입력해주세요.");
        }
        User user = findUser.get();
        User friendEntity = findFriendEntity.get();

        if(user.isFriendAcceptance()==false)
            throw new Exception("친구요청 허용 여부를 [참]으로 바꿔주세요. 그래야 친구 요청이 가능합니다.");
        if(friendEntity.isFriendAcceptance() == false)
            throw new Exception("요청을 보내는 상대의 친구허용 여부가 [거짓]이므로, 친구 요청을 보낼 수 없습니다.");

        Optional<Friend> findFriend = friendRepository.findByUserAndFriend(userId,friendEntity.getId());

        if(findFriend.isPresent()){ // 친구요청이 이미 존재한다면
            throw new Exception("이미 해당 친구에게 친구요청을 보냈습니다.");
        }

        Friend friend = Friend.builder()
                .id(UUID.randomUUID())
                .createdAt(LocalDateTime.now())
                .allow(false)
                .user(user)
                .friend(friendEntity)
                .build();

        friendRepository.save(friend);
    }

    public List<FriendResDto> searchUserFriendList() throws Exception {
        UUID userId = SecurityContextHolderUtils.getUserId();
        Optional<User> findUser = userRepository.findById(userId);

        if(!findUser.isPresent()) {
            throw new Exception("user not found");
        }
        User user = findUser.get();
        List<Friend> friendList = friendRepository.findUserFriend(userId);

        List<FriendResDto> friendResDtos = new ArrayList<>();
        friendList.forEach(friend->{
            friendResDtos.add(FriendResDto.toDto(friend));
        });
        return friendResDtos;
    }

    public List<FriendResDto.FriendRequest> searchReceiveFriendRequest() throws Exception {
        UUID userId = SecurityContextHolderUtils.getUserId();
        Optional<User> findUser = userRepository.findById(userId);

        if(!findUser.isPresent()) {
            throw new Exception("user not found");
        }
        User user = findUser.get();
        List<Friend> friendRequestList = friendRepository.findReceiveFriendRequest(userId);

        List<FriendResDto.FriendRequest> friendResDtos = new ArrayList<>();
        friendRequestList.forEach(friendReq -> friendResDtos.add(FriendResDto.FriendRequest.toDto(friendReq)));
        return friendResDtos;
    }

    public List<FriendResDto.FriendRequest> searchSendFriendRequest() throws Exception {
        UUID userId = SecurityContextHolderUtils.getUserId();
        Optional<User> findUser = userRepository.findById(userId);

        if(!findUser.isPresent()) {
            throw new Exception("user not found");
        }
        User user = findUser.get();
        List<Friend> friendRequestList = friendRepository.findSendFriendRequest(userId);

        List<FriendResDto.FriendRequest> friendResDtos = new ArrayList<>();
        friendRequestList.forEach(friendReq -> friendResDtos.add(FriendResDto.FriendRequest.toDto(friendReq)));
        return friendResDtos;
    }

    @Transactional
    public void acceptFriendRequest(FriendReqDto.ReqAccept req) throws Exception {
        UUID id = req.getId();
        Optional<Friend> findFriend = friendRepository.findById(id);

        if(!findFriend.isPresent()){
            throw new Exception("[id]확인 요망. 해당 id의 친구요청이 존재하지 않습니다.");
        }
        Friend friend = findFriend.get();
        friend.fixAllowance();

        Optional<Friend> temp = friendRepository.findByUserAndFriend(friend.getFriend().getId(), friend.getUser().getId());
        if(temp.isPresent()){
            Friend friendRequest = temp.get();
            friendRequest.fixAllowance();
            return;
        }

        Friend buildFriend = Friend.builder()
                .user(friend.getFriend())
                .friend(friend.getUser())
                .allow(true)
                .build();
        friendRepository.save(buildFriend);
    }

    public void deleteOne(FriendReqDto.ReqDeleteOne req) throws Exception {
        Optional<Friend> findFriend = friendRepository.findByIdFetchJoin(req.getId());
        if(!findFriend.isPresent())
            throw new Exception("[id] 확인 요망. id에 해당하는 친구요청이 존재하지 않습니다.");

        Friend friend = findFriend.get();
        friendRepository.delete(friend);
    }

}