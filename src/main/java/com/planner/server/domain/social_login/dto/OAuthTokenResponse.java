package com.planner.server.domain.social_login.dto;

import lombok.Data;

@Data
public class OAuthTokenResponse {

    String access_token;
    String token_type;
    String refresh_token;
    String expires_in;
    String scope;
    String refresh_token_expires_in;

}