package com.planner.server.domain.friend.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class FriendListDto {

    private int count;
    private List<FriendDto> friendDtoList = new ArrayList<>();

    public void addFriendDto(FriendDto friendDto){
        this.friendDtoList.add(friendDto);
    }

    public static FriendListDto toDto(List<FriendDto> friendDtos){
        List<FriendDto> temp = new ArrayList<>();
        friendDtos.stream().forEach(friendDto -> temp.add(friendDto));

        return FriendListDto.builder()
                .count(temp.size())
                .friendDtoList(temp)
                .build();
    }
}
