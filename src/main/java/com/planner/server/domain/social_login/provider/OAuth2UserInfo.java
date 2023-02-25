package com.planner.server.domain.social_login.provider;

public interface OAuth2UserInfo {

    String getProvider();
    String getProviderId();
    String getProfileName();
    String getProfileImagePath();

    String getProfileBirth();
}