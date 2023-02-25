package com.planner.server.domain.social_login.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SocialLoginReqDto {

    private String provider;
    private String code;
}
