package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()) // CSRF 보호 활성화
            )
            .authorizeRequests(authorizeRequests ->
                authorizeRequests
                    .requestMatchers("/api/login", "/api/signup").permitAll() // 로그인과 회원가입은 인증 없이 접근 가능
                    .anyRequest().authenticated() // 나머지 요청은 인증 필요
            )
            .formLogin(formLogin ->
                formLogin
                    .loginPage("/api/login") // 사용자 정의 로그인 페이지
                    .defaultSuccessUrl("/") // 로그인 성공 후 리다이렉션 URL
                    .permitAll()
            )
            .logout(logout ->
                logout.permitAll()
            )
            .sessionManagement(sessionManagement ->
                sessionManagement
                    .maximumSessions(1) // 세션 최대 허용 수
                    .maxSessionsPreventsLogin(false) // 중복 로그인 시 이전 로그인 세션 만료
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
