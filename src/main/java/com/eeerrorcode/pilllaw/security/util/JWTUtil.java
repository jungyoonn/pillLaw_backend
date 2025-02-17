package com.eeerrorcode.pilllaw.security.util;

import java.time.ZonedDateTime;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.MacAlgorithm;
import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class JWTUtil {
    @Value("${jwt.secret}")
    private String secretKey;

    private static final MacAlgorithm ALGORITHM = Jwts.SIG.HS256;
    private SecretKey key;

  @PostConstruct
  public void init() {
    try {
      if (secretKey == null || secretKey.trim().isEmpty()) {
        throw new IllegalArgumentException("JWT 시크릿 키가 설정되지 않았습니다.");
      }
      key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    } catch (Exception e) {
      log.error("JWT 키 초기화 오류: {}", e.getMessage());
      throw new RuntimeException("JWT 키 초기화 실패", e);
    }
  }

    public String generateToken(String email) {
        String token = Jwts.builder()
            .issuedAt(new Date())
            .expiration(Date.from(ZonedDateTime.now()
                .plusMonths(1L)
                .toInstant()))
            .signWith(key, ALGORITHM)
            .subject(email)
            .compact();
        
        // 생성된 토큰의 각 부분을 로그로 출력
        String[] parts = token.split("\\.");
        log.info("생성된 토큰 헤더: {}", parts[0]);
        log.info("생성된 토큰 페이로드: {}", parts[1]);
        log.info("생성된 토큰 서명: {}", parts[2]);
        
        return token;
    }

    private Claims parseToken(String token) {
        return Jwts.parser()
          .verifyWith(key)
          .build()
          .parseSignedClaims(token)
          .getPayload();
    }

    public String validateToken(String token) {
        log.info("토큰 검증 시작");
        try {
            String subject = parseToken(token).getSubject();
            log.info("토큰 검증 성공");
            return subject;
        } catch (JwtException e) {
            log.error("토큰 검증 실패");
            log.error("에러 타입: {}", e.getClass().getName());
            log.error("에러 메시지: {}", e.getMessage());
            return null;
        }
    }
}