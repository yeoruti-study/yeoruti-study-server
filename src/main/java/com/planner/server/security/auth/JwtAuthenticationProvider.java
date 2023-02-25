package com.planner.server.security.auth;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {
    private final CustomUserDetailsService customUserDetailsService;
    private final BCryptPasswordEncoder passwordEncoder;

    // 실제 인증을 담당
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        System.out.println("#=====AUTHENTICATION PROVIDER=====#");

        String username = token.getName();
        String password = token.getCredentials().toString();

        CustomUserDetails savedUser = (CustomUserDetails) customUserDetailsService.loadUserByUsername(username);
        String salt = savedUser.getUser().getSalt().toString();

        // salt된 password를 구한다
        String saltedPassword = password + salt;

        if(!passwordEncoder.matches(saltedPassword, savedUser.getPassword())) {
            throw new BadCredentialsException("로그인 정보가 올바르지 않습니다.");
        }
    
        return new UsernamePasswordAuthenticationToken(savedUser, password, savedUser.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
    
}
