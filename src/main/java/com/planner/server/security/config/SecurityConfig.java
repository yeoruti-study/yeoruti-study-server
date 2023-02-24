package com.planner.server.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .cors().disable()
            .csrf().disable()
            .formLogin().disable()
            .httpBasic().disable()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);    // jwt사용으로 세션관리 해제
        
        http
            .headers().frameOptions().sameOrigin();

        http
            .authorizeRequests()
            .antMatchers("/api/admin/**")
            .access("hasRole('ROLE_ADMIN')")
            .antMatchers("/api/test/**")
            .access("hasRole('ROLE_USER')")
            .antMatchers("/api/social-login/**", "/api/login")
            .permitAll()
            .anyRequest().authenticated();
        
        return http.build();
    }
}
