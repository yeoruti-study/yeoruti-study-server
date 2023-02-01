package com.planner.server.domain.friend.dto;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class FriendGetDto {

    private int count;
    private List<FriendResDto> friendList = new ArrayList<>();
    public void addFriendDto(FriendResDto friendDto){
        this.friendList.add(friendDto);
    }

    public static FriendGetDto toDto(List<FriendResDto> friendDtos){
        List<FriendResDto> temp = new ArrayList<>();
        friendDtos.stream().forEach(friendDto -> temp.add(friendDto));

        return FriendGetDto.builder()
                .count(temp.size())
                .friendList(temp)
                .build();
    }
}
