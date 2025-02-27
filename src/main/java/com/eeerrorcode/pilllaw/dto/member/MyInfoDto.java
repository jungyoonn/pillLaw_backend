package com.eeerrorcode.pilllaw.dto.member;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MyInfoDto {
  private MemberDto memberDto;
  private SocialMemberDto socialDto;
  private Integer follower;
  private Integer following;
  private AddressDto addressDto;
}
