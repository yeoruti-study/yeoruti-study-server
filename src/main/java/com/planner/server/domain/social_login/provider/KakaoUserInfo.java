package com.planner.server.domain.social_login.provider;

import java.util.Map;

public class KakaoUserInfo implements OAuth2UserInfo{

    private String providerId;
    private Map<String ,Object> attributes;
    private Map<String, Object> properties;
    private Map<String, Object> kakaoAccount;
    private Map<String, Object> profile;


    public KakaoUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
        properties = (Map)attributes.get("properties");
        kakaoAccount = (Map)attributes.get("kakao_account");
        profile = (Map)kakaoAccount.get("profile");
    }

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getProviderId() {
        return String.valueOf(attributes.get("id"));
    }

    @Override
    public String getProfileName() {
        return (String)properties.get("nickname");
    }

    @Override
    public String getProfileImagePath() {
        return (String)properties.get("profile_image");
    }

    @Override
    public String getProfileBirth() {
        return null;
    }
}