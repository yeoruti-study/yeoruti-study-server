package com.planner.server.domain.social_login.provider;

import com.planner.server.domain.social_login.dto.OAuthTokenResponse;
import com.planner.server.domain.social_login.dto.SocialLoginReqDto;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.StandardCharsets;
import java.util.Collections;

public class OauthTokenProvider
{

    public static OAuthTokenResponse getTokenFromAuthServer(SocialLoginReqDto req, ClientRegistration provider) throws Exception {
        String providerName = req.getProvider();
        String code = req.getCode();

        if(providerName.equals("naver")){
            return getNaverToken(code, provider);
        }else if(providerName.equals("kakao")){
            return getKakaoToken(code, provider);
        }else{
            throw new Exception("허용되지 않습니다.");
        }
    }

    public static OAuthTokenResponse getKakaoToken(String code, ClientRegistration provider){

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();

        formData.add("grant_type", "authorization_code");
        formData.add("client_id", provider.getClientId());
        formData.add("client_secret", provider.getClientSecret());
        formData.add("redirect_uri", provider.getRedirectUri());
        formData.add("code", code);

        String tokenUri = provider.getProviderDetails().getTokenUri();

        return WebClient.create()
                .post()
                .uri(provider.getProviderDetails().getTokenUri())
                .headers(header -> {
                    header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                    header.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));
                })
                .bodyValue(formData)
                .retrieve()
                .bodyToMono(OAuthTokenResponse.class)
                .block();
    }

    public static OAuthTokenResponse getNaverToken(String code, ClientRegistration provider){

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "authorization_code");
        formData.add("client_id", provider.getClientId());
        formData.add("client_secret", provider.getClientSecret());
        formData.add("code", code);
        formData.add("state", "1234");

        String tokenUri = provider.getProviderDetails().getTokenUri();

        return WebClient.create()
                .post()
                .uri(provider.getProviderDetails().getTokenUri())
                .headers(header -> {
                    header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                    header.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));
                })
                .bodyValue(formData)
                .retrieve()
                .bodyToMono(OAuthTokenResponse.class)
                .block();
    }

}
