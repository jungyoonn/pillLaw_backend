package com.eeerrorcode.pilllaw.dto.member;

import java.time.LocalDateTime;

import com.eeerrorcode.pilllaw.entity.member.LoginResult;
import com.eeerrorcode.pilllaw.entity.member.MemberAccount;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoginHistoryDto {
  private Long lno;
  private LocalDateTime loginTime;
  private String ip;
  private String device;
  private LoginResult loginResult;
  private String failureReason;
  private String provider;
  private MemberAccount loginType;
  private Long mno;
}
