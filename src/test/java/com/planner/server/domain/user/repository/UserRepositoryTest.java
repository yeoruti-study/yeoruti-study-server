package com.planner.server.domain.user.repository;

import com.planner.server.domain.user.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class UserRepositoryTest {

    @Autowired UserRepository userRepository;

    @Test
    void findByProfileName() {
        User user1 = userRepository.findByProfileName("프로필이름1");
        assertThat(user1.getUsername()).isEqualTo("히정수");
    }

    @Test
    void findById() {
        User user = User.builder()
                .username("김이박").build();
        User savedUser = userRepository.save(user);

        User findUser = userRepository.findById(user.getId()).get();
        assertThat(findUser.getUsername()).isEqualTo("김이박");
    }

    @Test
    void findAll(){
        List<User> allUsers = userRepository.findAll();
        assertThat(allUsers.size()).isEqualTo(2);
    }
}