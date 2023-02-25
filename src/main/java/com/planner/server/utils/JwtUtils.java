package com.planner.server.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.planner.server.domain.user.entity.User;
import com.planner.server.properties.AuthProperties;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@NoArgsConstructor
public class JwtUtils {
    private static long TOKEN_VALIDITY_IN_SECOND = 1000;
    private static long ACCESS_TOKEN_VALIDITY_TIME = TOKEN_VALIDITY_IN_SECOND * 60 * 15;
    private static long REFRESH_TOKEN_VALIDITY_TIME = TOKEN_VALIDITY_IN_SECOND * 60 * 60 * 24 * 3;

    public static String createAccessToken(User userEntity){
        return JWT.create()
                .withSubject("access_token")
                .withExpiresAt(new Date(System.currentTimeMillis()+ ACCESS_TOKEN_VALIDITY_TIME))
                .withClaim("username", userEntity.getUsername())
                .sign(Algorithm.HMAC512(AuthProperties.getAccessSecret()));
    }

    public static String createRefreshToken(User userEntity){
        return JWT.create()
                .withSubject("refresh_token")
                .withExpiresAt(new Date(System.currentTimeMillis()+ REFRESH_TOKEN_VALIDITY_TIME))
                .withClaim("username", userEntity.getUsername())
                .sign(Algorithm.HMAC512(AuthProperties.getRefreshSecret()));
    }

    public static String checkTokenUsername(String tokenVal) throws TokenExpiredException, JWTVerificationException {
        String username = JWT.require(Algorithm.HMAC512(AuthProperties.getAccessSecret())).build()
                .verify(tokenVal)
                .getClaim("username")
                .asString();
        return username;
    }
}