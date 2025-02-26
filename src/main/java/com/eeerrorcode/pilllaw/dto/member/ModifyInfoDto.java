package com.eeerrorcode.pilllaw.dto.member;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ModifyInfoDto {
  private MemberDto memberDto;
  private SocialMemberDto socialDto;
  private AddressDto address;
}
