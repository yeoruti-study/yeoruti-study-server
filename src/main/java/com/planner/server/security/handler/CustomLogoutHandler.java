package com.planner.server.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.planner.server.domain.message.Message;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomLogoutHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        Message message = new Message();
        message.setMessage("success");
        message.setStatus(HttpStatus.OK);
        message.setMemo("logout success");

        response.setStatus(message.getStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON.toString());    
        new ObjectMapper().writeValue(response.getOutputStream(), message);
    }
}
