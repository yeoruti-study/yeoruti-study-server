package com.planner.server.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.planner.server.domain.user.entity.User;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Service
@NoArgsConstructor
public class JwtUtils {

    @Value("${jwt.secret}")
    private String secret;

    @Value("{jwt.token-validity-in-seconds}")
    private Long tokenValidityInSeconds;

    private Long tokenValidityInMinutes = tokenValidityInSeconds * 60;

    private Long tokenValidityInHours = tokenValidityInMinutes * 60;

    private Long tokenValidityInDays = tokenValidityInHours * 24;

    private Long accessTokenValidity = tokenValidityInMinutes * 15;

    private Long refreshTokenValidity = tokenValidityInDays * 2;


    public String createAccessToken(User userEntity){

        return JWT.create()
                .withSubject("access_token")
                .withExpiresAt(new Date(System.currentTimeMillis()+ accessTokenValidity))
                .withClaim("id", userEntity.getId().toString())
                .withClaim("username", userEntity.getUsername())
                .sign(Algorithm.HMAC512(secret));
    }

    public String createRefreshToken(User userEntity){

        return JWT.create()
                .withSubject("refresh_token")
                .withExpiresAt(new Date(System.currentTimeMillis()+ refreshTokenValidity))
                .withClaim("id", userEntity.getId().toString())
                .withClaim("username", userEntity.getUsername())
                .sign(Algorithm.HMAC512(secret));
    }

    public String checkTokenUsername(String tokenVal) throws TokenExpiredException, JWTVerificationException {
        String username = JWT.require(Algorithm.HMAC512(secret)).build()
                .verify(tokenVal)
                .getClaim("username")
                .asString();
        return username;
    }

    private void issueNewAccessToken(HttpServletResponse response, User user) {
        String newAccessToken = createAccessToken(user);
        String jwtToken = createAccessToken(user);

        Cookie cookie = new Cookie("Authorization", jwtToken);
        cookie.setMaxAge(60*30);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        PrincipalDetails principalDetails = new PrincipalDetails(user);

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(principalDetails, null,principalDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}
