package com.eeerrorcode.pilllaw.dto.member;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ServiceAgreeDto {
  private Long mno;
  private boolean rule;
  private boolean info;
  private boolean tel;
  private boolean email;
}
