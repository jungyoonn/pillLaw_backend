package com.eeerrorcode.pilllaw.entity.member;

import com.eeerrorcode.pilllaw.entity.BaseEntity;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "tbl_deleted_member")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class DeletedMember extends BaseEntity{
  @Id
  private Long mno; // Member의 PK와 동일한 값 사용

  @OneToOne(fetch = FetchType.LAZY)
  @MapsId // mno를 PK이면서 FK로 사용
  @JoinColumn(name = "mno", unique = true)
  private Member member;

  private String email; // 암호화된 이메일

  private String deleteReason;
}
