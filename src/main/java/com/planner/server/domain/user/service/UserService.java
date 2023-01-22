package com.planner.server.domain.user.service;

import com.planner.server.domain.user.dto.*;
import com.planner.server.domain.user.entity.User;
import com.planner.server.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    // private final PasswordEncoder encoder;
    private final UserRepository userRepository;

    public String signUp(SignUpReqDto reqDto){

        String salt = UUID.randomUUID().toString();
//        String password = encoder.encode(reqDto.getPassword()+ salt.toString());
        String password = reqDto.getPassword() + salt;

        User user = User.builder()
                .id(UUID.randomUUID())
                .username(reqDto.getUsername())
                .password(password)
                .salt(salt)
                .roles("ROLE_USER")
                .profileName(reqDto.getProfileName())
                .profileAge(reqDto.getProfileAge())
                .profileImagePath(reqDto.getProfileImagePath())
                .alarmPermission(reqDto.isAlarmPermission())
                .friendAcceptance(reqDto.isFriendAcceptance())
                .createdAt(LocalDateTime.now())
                .build();

        return "SUCESS";
    }


    public User findById(UUID id){
        Optional<User> findUser = userRepository.findById(id);
        if(!findUser.isPresent()) {
            throw new IllegalArgumentException();
        }
        return findUser.get();
    }

    public UserListDto findAll() {
        List<User> users = userRepository.findAll();
        UserListDto userListDto = new UserListDto();
        userListDto.work(users);
        return userListDto;
    }

    public UserDto findByProfileName(String profileName){
        return UserDto.toDto(userRepository.findByProfileName(profileName));
    }

    @Transactional
    public boolean changeProfile(ProfileReqDto req){
        Optional<User> user = userRepository.findById(req.getId());

        if(!user.isPresent()){
            throw new IllegalArgumentException();
        }
        User findUser = user.get();
        findUser.fixProfile(req);
        return true;
    }

    public String deleteUser(DeleteReqDto req){
        Optional<User> findUser = userRepository.findById(req.getId());
        if(!findUser.isPresent()){
            throw new NullPointerException();
        }
        try{
            userRepository.delete(findUser.get());
        }
        catch (NullPointerException e){
            throw new NullPointerException(e.getMessage());
        }
        return "SUCCESS";
    }


}
