package com.eeerrorcode.pilllaw.dto.member;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SignUpDto {
  private ServiceAgreeDto terms;
  private MemberDto memberInfo;
}
