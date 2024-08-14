package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf
                .disable() // CSRF 보호 비활성화 (개발 중에만 사용)
            )
            .authorizeRequests(authz -> authz
                .requestMatchers("/api/register", "/api/login").permitAll() // 회원가입 및 로그인 요청을 모두 허용
                .anyRequest().authenticated() // 그 외의 모든 요청은 인증 필요
            )
            .formLogin(form -> form
                .disable() // 기본 로그인 폼 비활성화 (API를 사용할 때 필요)
            );

        return http.build();
    }
}
