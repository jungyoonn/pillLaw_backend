package com.eeerrorcode.pilllaw.entity.member;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.eeerrorcode.pilllaw.entity.BaseEntity;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "tbl_member")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Member extends BaseEntity implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long mno;

  private String email;

  private String password;

  private String name;

  private String nickname;

  private String tel;

  private boolean firstLogin;

  @Builder.Default
  @ElementCollection(fetch = FetchType.LAZY)
  private Set<MemberRole> roleSet = new HashSet<>();

  public void addMemberRole(MemberRole memberRole) {
    roleSet.add(memberRole);
  }

  @Builder.Default
  @ElementCollection(fetch = FetchType.LAZY)
  private Set<MemberStatus> statusSet = new HashSet<>();

  public void addMemberStatus(MemberStatus memberStatus) {
    statusSet.add(memberStatus);
  }

  @Builder.Default
  @ElementCollection(fetch = FetchType.LAZY)
  private Set<MemberAccount> accountSet = new HashSet<>();

  public void addMemberAccount(MemberAccount memberAccount) {
    accountSet.add(memberAccount);
  }
}
