package com.planner.server.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.planner.server.domain.user.entity.User;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import java.util.Date;

@NoArgsConstructor
public class JwtUtils {

    @Value("${jwt.secret}")
    private static String SECRET;

    @Value("${jwt.token-validity-in-seconds}")
    private static Long TOKEN_VALIDITY_IN_SECOND;

    private static final Long ACCESS_TOKEN_VALIDITY_TIME = TOKEN_VALIDITY_IN_SECOND * 60 * 15;

    private static final Long REFRESH_TOKEN_VALIDITY_TIME = TOKEN_VALIDITY_IN_SECOND * 60 * 60 * 24 * 3;


    public static String createAccessToken(User userEntity){

        return JWT.create()
                .withSubject("access_token")
                .withExpiresAt(new Date(System.currentTimeMillis()+ ACCESS_TOKEN_VALIDITY_TIME))
                .withClaim("username", userEntity.getUsername())
                .sign(Algorithm.HMAC512(SECRET));
    }

    public static String createRefreshToken(User userEntity){

        return JWT.create()
                .withSubject("refresh_token")
                .withExpiresAt(new Date(System.currentTimeMillis()+ REFRESH_TOKEN_VALIDITY_TIME))
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