package com.planner.server.domain.user.dto;

import com.planner.server.domain.user.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class UserListDto {

    public List<UserDto> userDtos = new ArrayList<>();

    public void work(List<User> users){
        for (User user : users) {
            UserDto userDto = UserDto.toDto(user);
            this.userDtos.add(userDto);
        }
    }

}
