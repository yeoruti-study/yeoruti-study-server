package com.planner.server.domain.friend.repository;

import com.planner.server.domain.friend.entity.Friend;
import com.planner.server.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FriendRepository extends JpaRepository<Friend, Long> {

    Optional<Friend> findById(UUID id);

    @Query("select distinct f from Friend f join fetch f.user join fetch f.friend where f.id = :id")
    Optional<Friend> findByIdFetchJoin(@Param("id") UUID id);

    @Query("select distinct f from Friend f join fetch f.user join fetch f.friend")
    List<Friend> findAll();

    @Query("select distinct f from Friend f join fetch f.user join fetch f.friend where f.user.id = :userId")
    List<Friend> findByUserId(@Param("userId") UUID id);

    @Query("select u from User u where u.id in :list ")
    List<User> validateUser(@Param("list") List<UUID> List);

    @Query("select f from Friend f where f.friend.id = :id")
    List<Friend> findByFriendId(@Param("id") UUID id);

    @Modifying
    @Query("delete from Friend f where f.friend.id = :id")
    void deleteById(@Param("id") UUID id);

    @Query("select f from Friend f where f.user.id = :userId and f.friend.id = :friendId")
    Optional<Friend> alreadyExists(@Param("userId") UUID userId,
                                   @Param("friendId") UUID friendId);

}
