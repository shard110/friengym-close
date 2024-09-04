package com.example.demo.config;

import java.util.Collections;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import io.jsonwebtoken.Claims;

public class JwtAuthentication extends AbstractAuthenticationToken {

    private final Claims claims;

    public JwtAuthentication(Claims claims) {
        super(Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))); // 기본 역할 할당
        this.claims = claims;
        setAuthenticated(true); // JWT 토큰이 유효하면 인증 상태로 설정
    }

    @Override
    public Object getCredentials() {
        return null; // 자격 증명은 JWT 자체이므로 별도로 필요 없음
    }

    @Override
    public Object getPrincipal() {
        return claims.getSubject(); // JWT의 subject를 사용자 식별자로 사용
    }
}
