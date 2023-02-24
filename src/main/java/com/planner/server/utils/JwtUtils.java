package com.planner.server.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.planner.server.domain.user.entity.User;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@NoArgsConstructor
public class JwtUtils {

    @Value("${jwt.secret}")
    private String SECRET;

    private long TOKEN_VALIDITY_IN_SECOND = 1000;

    private long ACCESS_TOKEN_VALIDITY_TIME = TOKEN_VALIDITY_IN_SECOND * 60 * 15;

    private long REFRESH_TOKEN_VALIDITY_TIME = TOKEN_VALIDITY_IN_SECOND * 60 * 60 * 24 * 3;


    public String createAccessToken(User userEntity){
        return JWT.create()
                .withSubject("access_token")
                .withExpiresAt(new Date(System.currentTimeMillis()+ ACCESS_TOKEN_VALIDITY_TIME))
                .withClaim("username", userEntity.getUsername())
                .sign(Algorithm.HMAC512(SECRET));
    }

    public String createRefreshToken(User userEntity){
        return JWT.create()
                .withSubject("refresh_token")
                .withExpiresAt(new Date(System.currentTimeMillis()+ REFRESH_TOKEN_VALIDITY_TIME))
                .withClaim("username", userEntity.getUsername())
                .sign(Algorithm.HMAC512(SECRET));
    }

    public String checkTokenUsername(String tokenVal) throws TokenExpiredException, JWTVerificationException {
        String username = JWT.require(Algorithm.HMAC512(SECRET)).build()
                .verify(tokenVal)
                .getClaim("username")
                .asString();
        return username;
    }

}