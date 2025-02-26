package com.eeerrorcode.pilllaw.entity.member;

import com.eeerrorcode.pilllaw.entity.BaseEntity;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "tbl_member_address")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class MemberAddress extends BaseEntity{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long addrno;

  private String recipient;

  private String tel;

  private String postalCode;

  private String roadAddress;

  private String detailAddress;

  private boolean defaultAddr;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "mno")
  private Member member;
}
