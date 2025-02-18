package com.eeerrorcode.pilllaw.security.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {
  private String email;
  private String password;
  @JsonProperty("remember-me")  // JSON의 "remember-me"를 rememberMe 필드와 매핑
  private boolean rememberMe;
}
