package com.eeerrorcode.pilllaw.dto.member;

import java.util.ArrayList;
import java.util.List;

import com.eeerrorcode.pilllaw.entity.member.MemberAccount;
import com.eeerrorcode.pilllaw.entity.member.MemberRole;
import com.eeerrorcode.pilllaw.entity.member.MemberStatus;

import lombok.*;
import lombok.Builder.Default;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberDto {
  private long mno;
  private String email;
  private String password;
  private String name;
  private String nickname;
  private String tel;
  private boolean firstLogin;

  @Default
  private List<MemberRole> roles = new ArrayList<>();

  @Default
  private List<MemberAccount> accounts = new ArrayList<>();

  @Default
  private List<MemberStatus> status = new ArrayList<>();

  public void addRole(MemberRole role) {
    if (this.roles == null) {
      this.roles = new ArrayList<>();
    }
    this.roles.add(role);
  }

  public void addAccount(MemberAccount account) {
    if (this.accounts == null) {
      this.accounts = new ArrayList<>();
    }
    this.accounts.add(account);
  }

  public boolean hasRole(MemberRole role) {
    return roles.contains(role);
  }

  public boolean isAdmin() {
    return roles.contains(MemberRole.ADMIN);
  }

  public boolean isSubscriber() {
    return roles.contains(MemberRole.SUBSCRIBER);
  }
}
