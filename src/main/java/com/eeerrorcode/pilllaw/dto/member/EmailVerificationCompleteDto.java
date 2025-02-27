package com.eeerrorcode.pilllaw.dto.member;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmailVerificationCompleteDto {
  private Long mno;
  private String email;
}
