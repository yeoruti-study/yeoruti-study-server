package com.planner.server.domain.friend.dto;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class FriendListDto {

    private int count;
    private List<FriendDto> friendList = new ArrayList<>();

    public void addFriendDto(FriendDto friendDto){
        this.friendList.add(friendDto);
    }

    public static FriendListDto toDto(List<FriendDto> friendDtos){
        List<FriendDto> temp = new ArrayList<>();
        friendDtos.stream().forEach(friendDto -> temp.add(friendDto));

        return FriendListDto.builder()
                .count(temp.size())
                .friendList(temp)
                .build();
    }
}
