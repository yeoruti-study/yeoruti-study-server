package com.planner.server.domain.friend.repository;

import com.planner.server.domain.friend.entity.Friend;
import com.planner.server.domain.friend.service.FriendService;
import com.planner.server.domain.user.entity.User;
import com.planner.server.domain.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.UUID;


@SpringBootTest
@Transactional
class FriendRepositoryTest {

    @Autowired FriendRepository friendRepository;
    @Autowired
    FriendService friendService;
    @Autowired
    UserRepository userRepository;

    @Test
    void findById() {
        UUID uuid = UUID.randomUUID();
        Friend friend = Friend.builder()
                .id(uuid)
                .allow(false).build();

        Friend savedFriend = friendRepository.save(friend);
        Friend findFriend = friendRepository.findById(uuid).get();

        Assertions.assertThat(findFriend.getCid()).isEqualTo(savedFriend.getCid());
        Assertions.assertThat(findFriend.getCreatedAt()).isEqualTo(savedFriend.getCreatedAt());

    }

    @Test
    void findByUserId() {
    }

    @Test
    void findByFriendId() {
    }
}