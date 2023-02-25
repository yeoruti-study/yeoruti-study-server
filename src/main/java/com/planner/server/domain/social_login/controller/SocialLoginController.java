package com.planner.server.domain.social_login.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.planner.server.domain.message.Message;
import com.planner.server.domain.social_login.dto.LoginResponse;
import com.planner.server.domain.social_login.dto.SocialLoginReqDto;
import com.planner.server.domain.social_login.service.SocialLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@RestController
@RequestMapping("/api/social-login")
@RequiredArgsConstructor
public class SocialLoginController {

    private final SocialLoginService socialLoginService;

    @PostMapping("")
    public void socialLogin(HttpServletResponse response, @RequestBody SocialLoginReqDto req) throws IOException {

        ObjectMapper om = new ObjectMapper();
        response.setContentType(MediaType.APPLICATION_JSON.toString());

        LoginResponse loginResponse = null;
        try {
            loginResponse = socialLoginService.socialLogin(req);
        } catch (Exception e) {
            Message message = Message.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message("error")
                    .memo(e.getMessage())
                    .build();
            om.writeValue(response.getOutputStream(), message);
            return;
        }
        Message message = Message.builder()
                .data(loginResponse)
                .status(HttpStatus.OK)
                .message("success")
                .memo("소셜 로그인 성공. 엑세스 토큰을 발급힙니다.")
                .build();

        String accessToken = loginResponse.getAccess_token();

        ResponseCookie cookie = ResponseCookie.from("Authorization", accessToken)
                .httpOnly(true)
                .domain("localhost")
                .sameSite("Strict")
                .path("/")
                .maxAge(60 * 60 * 3) // 3시간
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        om.writeValue(response.getOutputStream(), message);
    }


}