package com.eeerrorcode.pilllaw.entity.member;

import java.time.LocalDateTime;

import com.eeerrorcode.pilllaw.entity.BaseEntity;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "tbl_login_history")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class LoginHistory extends BaseEntity{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long lno;

  private LocalDateTime loginTime;

  private String ip;

  private String device;

  private boolean success;

  private String failureReason;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "mno")
  private Member member;
}
