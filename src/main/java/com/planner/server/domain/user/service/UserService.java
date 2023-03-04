package com.planner.server.domain.user.service;

import com.planner.server.domain.refresh_token.entity.RefreshToken;
import com.planner.server.domain.refresh_token.repository.RefreshTokenRepository;
import com.planner.server.domain.user.dto.*;
import com.planner.server.domain.user.entity.User;
import com.planner.server.domain.user.repository.UserRepository;
import com.planner.server.utils.SecurityContextHolderUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

     private final BCryptPasswordEncoder encoder;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    public void createOne(UserReqDto.ReqCreateOne reqDto) throws Exception{

        UUID salt = UUID.randomUUID();
        String password = reqDto.getPassword() + salt.toString();
        String encodedPassword = encoder.encode(password);

        if(userRepository.findByUsername(reqDto.getUsername()).isPresent()){
            throw new Exception("해당 유저네임이 이미 존재합니다. 다른 이름을 입력해주세요.");
        }

        User user = User.builder()
                .id(UUID.randomUUID())
                .username(reqDto.getUsername())
                .password(encodedPassword)
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
    public void changeUserInfo(UserReqDto.ReqUpdateProfile req) throws IllegalArgumentException{
        UUID userId = SecurityContextHolderUtils.getUserId();
        Optional<User> user = userRepository.findById(userId);

        if(!user.isPresent()){
            throw new IllegalArgumentException("id에 부합하는 유저가 존재하지 않습니다. 다시 입력해주세요.");
        }
        User findUser = user.get();
        findUser.changeUserInfo(req);
    }

    public void deleteUser(UserReqDto.ReqDeleteUser req) throws Exception {
        String username = SecurityContextHolderUtils.getUsername();
        if(!req.getUsername().equals(username)){
            throw new Exception("username 확인 요망");
        }
        Optional<User> findUser = userRepository.findByUsername(username);
        User user = findUser.get();

        String password = user.getPassword();
        String inputPassword = req.getPassword() + user.getSalt();

        if(encoder.matches(inputPassword, password)){
            userRepository.delete(user);
            Optional<RefreshToken> findRefreshToken = refreshTokenRepository.findByUserId(user.getId());
            if(findRefreshToken.isPresent()){
                refreshTokenRepository.delete(findRefreshToken.get());
            }
        }else
            throw new Exception("password 확인 요망");
    }

    public boolean checkPassword(User findUser, String inputPassword) {
        String userPassword = findUser.getPassword();
        inputPassword = inputPassword + findUser.getSalt();
        return encoder.matches(inputPassword, userPassword);
    }
}
