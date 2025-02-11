package com.eeerrorcode.pilllaw.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "tbl_service_agree")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class ServiceAgree extends BaseEntity{
  @Id
  private Long mno; // Member의 PK와 동일한 값 사용

  @OneToOne(fetch = FetchType.LAZY)
  @MapsId // mno를 PK이면서 FK로 사용
  @JoinColumn(name = "mno", unique = true)
  private Member member;

  private boolean rule;
  private boolean info;
  private boolean tel;
  private boolean email;
}