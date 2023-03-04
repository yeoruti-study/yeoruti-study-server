package com.planner.server.utils;

import com.planner.server.security.auth.CustomUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

public class SecurityContextHolderUtils {

    public static UUID getUserId(){
        CustomUserDetails principal = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UUID userId = principal.getUser().getId();
        return userId;
    }

    public static String getUsername(){
        CustomUserDetails principal = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal.getUsername();
        return username;
    }

    public static String getUserPassword(){
        CustomUserDetails principal = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String password = principal.getPassword();
        return password;
    }
}
