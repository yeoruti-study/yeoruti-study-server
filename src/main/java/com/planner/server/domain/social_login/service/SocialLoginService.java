package com.planner.server.domain.social_login.service;

import com.planner.server.domain.refresh_token.entity.RefreshToken;
import com.planner.server.domain.refresh_token.repository.RefreshTokenRepository;
import com.planner.server.domain.social_login.dto.LoginResponse;
import com.planner.server.domain.social_login.dto.OAuthTokenResponse;
import com.planner.server.domain.social_login.dto.SocialLoginReqDto;
import com.planner.server.domain.social_login.provider.OauthTokenProvider;
import com.planner.server.domain.social_login.provider.OauthUserInfoProvider;
import com.planner.server.domain.user.entity.User;
import com.planner.server.domain.user.repository.UserRepository;
import com.planner.server.domain.social_login.provider.OAuth2UserInfo;
import com.planner.server.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class SocialLoginService {

    private final String BEARER_TYPE = "Bearer";
    private final ClientRegistrationRepository clientRegistrationRepository;

    private final RefreshTokenRepository refreshTokenRepository;

    private final JwtUtils jwtUtils;

    private final UserRepository userRepository;

    public LoginResponse socialLogin(SocialLoginReqDto req) throws Exception {

        ClientRegistration provider = clientRegistrationRepository.findByRegistrationId(req.getProvider());

        OAuthTokenResponse tokenResponse = OauthTokenProvider.getTokenFromAuthServer(req, provider);
        String providerName = req.getProvider();
        Map<String, Object> userAttributes = OauthUserInfoProvider.getUserInfoFromAuthServer(providerName, provider, tokenResponse);

        OAuth2UserInfo oauth2UserInfo = OauthUserInfoProvider.makeOAuth2UserInfo(providerName, userAttributes);
        providerName = oauth2UserInfo.getProvider();
        String providerId = oauth2UserInfo.getProviderId();

        String username = providerName +"_"+oauth2UserInfo.getProviderId();
        String password = UUID.randomUUID().toString();

        Optional<User> findUser = userRepository.findByUsername(username);
        User userEntity = null;

        if(!findUser.isPresent()){
            userEntity = User.builder()
                    .id(UUID.randomUUID())
                    .username(username)
                    .password(password)
                    .roles("ROLE_USER")
                    .provider(providerName)
                    .profileImagePath(oauth2UserInfo.getProfileImagePath())
                    .profileName(oauth2UserInfo.getProfileName())
                    .profileBirth(oauth2UserInfo.getProfileBirth())
                    .createdAt(LocalDateTime.now())
                    .build();
            userRepository.save(userEntity);
        }else{
            userEntity = findUser.get();
        }
        String accessToken = jwtUtils.createAccessToken(userEntity);
        String refreshToken = jwtUtils.createRefreshToken(userEntity);

        Optional<RefreshToken> oldRefreshToken = refreshTokenRepository.findByUserId(userEntity.getId());
        if(oldRefreshToken.isPresent()){ // 해당 사용자의 리프레쉬 토큰이 이미 있다면 삭제한다.
            refreshTokenRepository.delete(oldRefreshToken.get());
        }
        RefreshToken newRefreshToken = RefreshToken.builder()
                .id(UUID.randomUUID())
                .value(refreshToken)
                .userId(userEntity.getId())
                .build();
        refreshTokenRepository.save(newRefreshToken);
        
        return LoginResponse.builder()
                .token_type(BEARER_TYPE)
                .access_token(accessToken)
                .build();
    }

}