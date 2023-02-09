package com.planner.server.domain.user.dto;

import com.planner.server.domain.user.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class UserGetDto {

    public List<UserResDto> userResDtos = new ArrayList<>();

    public void work(List<User> users){
        for (User user : users) {
            UserResDto userResDto = UserResDto.toDto(user);
            this.userResDtos.add(userResDto);
        }
    }

}
