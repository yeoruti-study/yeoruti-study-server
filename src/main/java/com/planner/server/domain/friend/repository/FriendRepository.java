package com.planner.server.domain.friend.repository;

import com.planner.server.domain.friend.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FriendRepository extends JpaRepository<Friend, Long> {

    Optional<Friend> findById(UUID id);

    @Query("select f from Friend f where f.user.id = :userId")
    List<Friend> findByUserId(@Param("userId") UUID id);

    @Query("select f from Friend f where f.friend.id = :id")
    List<Friend> findByFriendId(@Param("id") UUID id);

}
