package com.planner.server.domain.friend.repository;

import com.planner.server.domain.friend.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FriendRepository extends JpaRepository<Friend, Long> {

    @Query("select f from Friend f where f.id = :id")
    Optional<Friend> findById(@Param("id") UUID id);

    @Query("select f from Friend f where f.user.id = :userId")
    List<Friend> findByUserId(@Param("userId") UUID id);

    @Query("select f from Friend f where f.friend.id = :id")
    List<Friend> findByFriendId(@Param("id") UUID id);

    @Query("select f from Friend f where f.user.id = :userId and f.friend.id = :friendId")
    Optional<Friend> alreadyExists(@Param("userId") UUID userId,
                                   @Param("friendId") UUID friendId);

}
