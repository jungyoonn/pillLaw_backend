package com.eeerrorcode.pilllaw.dto.member;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmailDto {
  private String email;
  private String authCode;
}
