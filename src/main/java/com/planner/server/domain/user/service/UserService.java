package com.planner.server.domain.user.service;

import com.planner.server.domain.user.dto.*;
import com.planner.server.domain.user.entity.User;
import com.planner.server.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    // private final PasswordEncoder encoder;
    private final UserRepository userRepository;

    public void createOne(UserReqDto reqDto) throws Exception{

        String salt = UUID.randomUUID().toString();
        String password = reqDto.getPassword() + salt;

        if(userRepository.findByUsername(reqDto.getUsername()).isPresent()){
            throw new Exception("해당 유저네임이 이미 존재합니다. 다른 이름을 입력해주세요.");
        }

        User user = User.builder()
                .id(UUID.randomUUID())
                .username(reqDto.getUsername())
                .password(password)
                .salt(salt)
                .roles("ROLE_USER")
                .profileName(reqDto.getProfileName())
                .profileBirth(reqDto.getProfileBirth())
                .profileImagePath(reqDto.getProfileImagePath())
                .alarmPermission(reqDto.isAlarmPermission())
                .friendAcceptance(reqDto.isFriendAcceptance())
                .createdAt(LocalDateTime.now())
                .build();

        userRepository.save(user);
    }

    public User findOne(UUID id) throws Exception {
        if(id==null) {
            throw new IllegalArgumentException("parameter:[id] is null");
        }
        Optional<User> findUser = userRepository.findById(id);
        if(!findUser.isPresent()) {
            throw new Exception("parameter:[id] is wrong");
        }
        return findUser.get();
    }


    public List<UserResDto> findAll() {
        List<User> users = userRepository.findAll();
        List<UserResDto> userResDtos = new ArrayList<>();
        users.forEach(user->userResDtos.add(UserResDto.toDto(user)));

        return userResDtos;
    }


    @Transactional
    public void changeUserInfo(UserReqDto req) throws IllegalArgumentException{
        Optional<User> user = userRepository.findById(req.getId());

        if(!user.isPresent()){
            throw new IllegalArgumentException("id에 부합하는 유저가 존재하지 않습니다. 다시 입력해주세요.");
        }
        User findUser = user.get();
        findUser.changeUserInfo(req);
    }

    @Transactional
    public void deleteOne(UUID userId) throws Exception {
        Optional<User> byId = userRepository.findById(userId);
        if(!byId.isPresent()){
            throw new Exception("[id] 값 확인요망. 유저 데이터가 없습니다.");
        }
        userRepository.delete(byId.get());
    }

}
