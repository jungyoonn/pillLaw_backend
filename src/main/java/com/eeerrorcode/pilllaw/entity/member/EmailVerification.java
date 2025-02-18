package com.eeerrorcode.pilllaw.entity.member;

import java.time.Instant;

import com.eeerrorcode.pilllaw.entity.BaseEntity;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "tbl_EmailVerification")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class EmailVerification extends BaseEntity{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String email; // 인증할 이메일

  @Column(nullable = false, unique = true)
  private String token; // 인증 토큰 (UUID 사용)

  @Column(nullable = false)
  private Instant expiresAt; // 만료 시간

}
