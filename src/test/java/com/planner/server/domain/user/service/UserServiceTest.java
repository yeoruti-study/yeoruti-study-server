package com.planner.server.domain.user.service;

import com.planner.server.domain.user.dto.SaveUserReqDto;
import com.planner.server.domain.user.dto.SaveUserResDto;
import com.planner.server.domain.user.dto.UserDto;
import com.planner.server.domain.user.entity.User;
import com.planner.server.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
@Rollback(value = false)
class UserServiceTest {

    @Autowired UserRepository userRepository;
    @Autowired UserService userService;

    @Test
    void saveUserTest() {
        SaveUserReqDto reqDto = SaveUserReqDto.builder()
                .username("히정수")
                .password("1235")
                .profileName("프로필이름1")
                .profileAge(25)
                .profileImagePath("/abac/def")
                .alarmPermission(true)
                .friendAcceptance(false)
                .build();

        SaveUserResDto savedUser = userService.save(reqDto);

        assertThat(savedUser.getUsername()).isEqualTo("하정수");
    }

}