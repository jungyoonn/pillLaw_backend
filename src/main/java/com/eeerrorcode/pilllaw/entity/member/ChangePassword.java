package com.eeerrorcode.pilllaw.entity.member;

import com.eeerrorcode.pilllaw.entity.BaseEntity;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "tbl_change_password")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class ChangePassword extends BaseEntity{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long cpno;

  private String password;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "mno", nullable = false)
  private Member member;
}
