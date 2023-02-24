package com.planner.server.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.planner.server.domain.user.entity.User;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@NoArgsConstructor
public class JwtUtils {

    @Value("${jwt.secret}")
    private static String SECRET;

    @Value("{jwt.token-validity-in-seconds}")
    private static Long TOKEN_VALIDITY_IN_SECOND;

    private static Long TOKEN_VALIDITY_IN_MINUTE = TOKEN_VALIDITY_IN_SECOND * 60;

    private static Long TOKEN_VALIDITY_IN_HOUR = TOKEN_VALIDITY_IN_MINUTE * 60;

    private static Long TOKEN_VALIDITY_IN_DAY = TOKEN_VALIDITY_IN_HOUR* 24;

    private static Long ACCESS_TOKEN_VALIDITY = TOKEN_VALIDITY_IN_MINUTE * 15;

    private static Long REFRESH_TOKEN_VALIDITY = TOKEN_VALIDITY_IN_DAY * 2;


    public static String createAccessToken(User userEntity){

        return JWT.create()
                .withSubject("access_token")
                .withExpiresAt(new Date(System.currentTimeMillis()+ ACCESS_TOKEN_VALIDITY))
                .withClaim("username", userEntity.getUsername())
                .sign(Algorithm.HMAC512(SECRET));
    }

    public static String createRefreshToken(User userEntity){

        return JWT.create()
                .withSubject("refresh_token")
                .withExpiresAt(new Date(System.currentTimeMillis()+ REFRESH_TOKEN_VALIDITY))
                .withClaim("username", userEntity.getUsername())
                .sign(Algorithm.HMAC512(SECRET));
    }

    public static String checkTokenUsername(String tokenVal) throws TokenExpiredException, JWTVerificationException {
        String username = JWT.require(Algorithm.HMAC512(SECRET)).build()
                .verify(tokenVal)
                .getClaim("username")
                .asString();
        return username;
    }

}