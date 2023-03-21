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
    @Query("select distinct f from Friend f join fetch f.user join fetch f.friend where f.id = :id")
    Optional<Friend> findByIdFetchJoin(@Param("id") UUID id);

    @Query("select f from Friend f where f.user.id = :userId and f.friend.id = :friendId")
    Optional<Friend> findByUserAndFriend(@Param("userId") UUID userId, @Param("friendId") UUID friendId);
    @Query("select f from Friend f where f.user.id = :userId and f.allow = true")
    List<Friend> findUserFriend(@Param("userId") UUID userId);

    @Query("select f from Friend f where f.friend.id = :userId and f.allow = false")
    List<Friend> findReceiveFriendRequest(@Param("userId") UUID userId);

    @Query("select f from Friend f where f.user.id = :userId and f.allow = false")
    List<Friend> findSendFriendRequest(@Param("userId") UUID userId);
}