package com.planner.server.domain.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChangeProfileReqDto {

    private Long cid;
    private String profileName;
    private int profileAge;
    private String profileImagePath;

}
