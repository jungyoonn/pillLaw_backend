package com.eeerrorcode.pilllaw.dto.member;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmailDto {
  private Long id;
  private String email;
  private String token;
  private Instant expiresAt;
  private LocalDateTime regDate;
  private LocalDateTime modDate;

  public EmailDto(String email, long expirationMillis) {
    this.email = email;
    this.token = UUID.randomUUID().toString(); // 랜덤한 고유 토큰 생성
    this.expiresAt = Instant.now().plusMillis(expirationMillis); // 만료 시간 설정
  }

  public boolean isExpired() {
    return Instant.now().isAfter(expiresAt);
  }
}
