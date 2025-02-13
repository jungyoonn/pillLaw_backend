package com.eeerrorcode.pilllaw.security.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.eeerrorcode.pilllaw.entity.member.MemberAccount;
import com.eeerrorcode.pilllaw.entity.member.MemberRole;
import com.eeerrorcode.pilllaw.entity.member.MemberStatus;

import lombok.*;

@Getter
@Setter
@ToString
public class AuthMemberDto extends User {
  private long mno;
  private String email;
  private String password;
  private String name;
  private String nickname;
  private String tel;
  private boolean firstLogin;

  private List<MemberRole> roles = new ArrayList<>();

  private List<MemberAccount> accounts = new ArrayList<>();

  private List<MemberStatus> status = new ArrayList<>();
  
  // security 자체 로그인 호출 생성자
  public AuthMemberDto(String username, String password, Long mno, List<MemberAccount> accounts, String name, String nickname, String tel, boolean firstLogin, Collection<? extends GrantedAuthority> authorities) {
    super(username, password, authorities);
    this.name = name;
    this.mno = mno;
    this.nickname = nickname;
    this.tel = tel;
    this.firstLogin = firstLogin;
    this.email = username;
    this.accounts = accounts;
    this.password = password;
  }
}
