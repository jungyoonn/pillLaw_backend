package com.eeerrorcode.pilllaw.security.util;

import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class JWTUtil {
    private final SecretKey key;

    public JWTUtil(@Value("${jwt.secret}") String secretKey) {
        log.info("JWTUtil 초기화 시작");
        try {
            byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
            log.info("키 바이트 길이: {}", keyBytes.length);
            
            this.key = Keys.hmacShaKeyFor(keyBytes);
            log.info("SecretKey 생성 성공");
        } catch (Exception e) {
            log.error("SecretKey 생성 실패", e);
            throw e;
        }
    }

    public String generateToken(String content) throws Exception {
        log.info("토큰 생성 시작. 내용: {}", content);
        try {
            String token = Jwts.builder()
                .header()
                .add("typ", "JWT")
                .and()
                .issuedAt(new Date())
                .expiration(Date.from(ZonedDateTime.now()
                    .plusMonths(1L)
                    .toInstant()))
                .subject(content)
                .signWith(key, Jwts.SIG.HS384)
                .compact();
            
            // 생성된 토큰의 각 부분을 로그로 출력
            String[] parts = token.split("\\.");
            log.info("생성된 토큰 헤더: {}", parts[0]);
            log.info("생성된 토큰 페이로드: {}", parts[1]);
            log.info("생성된 토큰 서명: {}", parts[2]);
            
            return token;
        } catch (Exception e) {
            log.error("토큰 생성 실패", e);
            throw e;
        }
    }

    public String validateAndExtract(String tokenStr) {
        log.info("토큰 검증 시작");
        try {
            Jws<Claims> defaultJws = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(tokenStr);
            
            Claims claims = defaultJws.getPayload();
            String subject = claims.getSubject();
            
            log.info("토큰 검증 성공");
            log.info("Subject: {}", subject);
            
            return subject;
        } catch (Exception e) {
            log.error("토큰 검증 실패");
            log.error("에러 타입: {}", e.getClass().getName());
            log.error("에러 메시지: {}", e.getMessage());
            
            // 디버깅을 위해 토큰 각 부분 로깅
            try {
                String[] parts = tokenStr.split("\\.");
                log.error("검증 실패한 토큰 헤더: {}", parts[0]);
                log.error("검증 실패한 토큰 페이로드: {}", parts[1]);
                log.error("검증 실패한 토큰 서명: {}", parts[2]);
            } catch (Exception ex) {
                log.error("토큰 분석 실패");
            }
            
            throw e;
        }
    }
}