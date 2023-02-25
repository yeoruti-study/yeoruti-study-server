package com.planner.server.domain.social_login.provider;

import java.util.Map;

public class NaverUserInfo implements OAuth2UserInfo {

    private Map<String, Object> attributes;

    public NaverUserInfo(Map<String, Object> attributes) {
        this.attributes = (Map) attributes.get("response");
    }

    @Override
    public String getProvider() {
        return "naver";
    }

    @Override
    public String getProviderId() {
        return (String) attributes.get("id");
    }

    @Override
    public String getProfileName() {
        return (String) attributes.get("nickname");
    }

    @Override
    public String getProfileImagePath() {
        return (String) attributes.get("profile_image");
    }

    public String getProfileBirth(){
        String birthYear = (String) attributes.get("birthyear");
        String birthDay = (String) attributes.get("birthday");
        return birthYear+"-"+birthDay;
    }
}