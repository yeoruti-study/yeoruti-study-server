package com.planner.server.domain.friend.service;

import com.planner.server.domain.friend.repository.FriendRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class FriendServiceTest {

    @Autowired
    FriendRepository friendRepository;

    @Autowired
    FriendService friendService;

    @Test
    void save() {
    }

    @Test
    void findFriend() {
    }

    @Test
    void findAll() {

    }

    @Test
    void updateAllowance() {
    }

    @Test
    void deleteFriend() {
    }
}