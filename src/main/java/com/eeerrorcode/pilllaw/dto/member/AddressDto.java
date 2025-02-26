package com.eeerrorcode.pilllaw.dto.member;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AddressDto {
  private Long addrno;
  private String recipient;
  private String tel;
  private String postalCode;
  private String roadAddress;
  private String detailAddress;
  private boolean defaultAddr;
  private Long mno;
}
