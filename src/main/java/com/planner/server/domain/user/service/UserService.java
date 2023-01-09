package com.planner.server.domain.user.service;

import com.planner.server.domain.user.dto.*;
import com.planner.server.domain.user.entity.User;
import com.planner.server.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    // private final PasswordEncoder encoder;
    private final UserRepository userRepository;

    public SaveUserResDto save(SaveUserReqDto reqDto){

        UUID id = UUID.randomUUID();
        UUID salt = UUID.randomUUID();
//        String password = encoder.encode(reqDto.getPassword()+ salt.toString());
        String password = reqDto.getPassword() + salt;

        User user = User.builder()
                .id(id)
                .username(reqDto.getUsername())
                .password(password)
                .salt(salt)
                .roles("ROLE_USER")
                .profileName(reqDto.getProfileName())
                .profileAge(reqDto.getProfileAge())
                .profileImagePath(reqDto.getProfileImagePath())
                .alarmPermission(reqDto.isAlarmPermission())
                .friendAcceptance(reqDto.isAlarmPermission())
                .createdAt(LocalDateTime.now())
                .build();

        return SaveUserResDto.toDto(userRepository.save(user));
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

    public void changeProfile(ChangeProfileReqDto reqDto){
        Long cid = reqDto.getCid();
        userRepository.findById(cid);
    }

    public void changePassword(User user, String password){
        // String userPassword = encodePassword(password);

        user.fixPassword(password);
        user.update();
    }

    public void changeAlarmPermission(User user){
        user.fixAlarmPermission();
    }

    public void changeFriendAcceptance(User user){
        user.fixFriendAcceptance();
    }

    public void delete(User user){
        userRepository.delete(user);
    }

}
