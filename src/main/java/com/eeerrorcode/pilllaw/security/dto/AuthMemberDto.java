package com.eeerrorcode.pilllaw.security.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.eeerrorcode.pilllaw.dto.member.SocialMemberDto;
import com.eeerrorcode.pilllaw.entity.member.MemberAccount;
import com.eeerrorcode.pilllaw.entity.member.MemberRole;
import com.eeerrorcode.pilllaw.entity.member.MemberStatus;

import lombok.*;

@Getter
@Setter
@ToString
public class AuthMemberDto extends User implements OAuth2User{
  private Long mno;
  private String email;
  private String password;
  private String name;
  private String nickname;
  private String tel;
  private Boolean firstLogin;
  private Map<String, Object> attr;
  private SocialMemberDto dto;

  private List<MemberRole> roles = new ArrayList<>();

  private List<MemberAccount> accounts = new ArrayList<>();

  private List<MemberStatus> status = new ArrayList<>();
  
  // security 자체 로그인 호출 생성자
  public AuthMemberDto(String username, String password, Long mno, List<MemberAccount> accounts, List<MemberRole> roles
  , List<MemberStatus> status, String name, String nickname, String tel, boolean firstLogin
  , Collection<? extends GrantedAuthority> authorities) {
    super(username, password, authorities);
    this.name = name;
    this.mno = mno;
    this.nickname = nickname;
    this.tel = tel;
    this.firstLogin = firstLogin;
    this.email = username;
    this.accounts = accounts;
    this.roles = roles;
    this.status = status;
    this.password = password;
  }

  // OAuth2 호출 생성자
  public AuthMemberDto(String username, String password, Long mno, List<MemberAccount> accounts, List<MemberRole> roles
  ,List<MemberStatus> status, String name, String nickname, String tel, boolean firstLogin
  ,Collection<? extends GrantedAuthority> authorities, Map<String, Object> attr, SocialMemberDto dto) {
    super(username, password != null ? password : "SOCIAL_LOGIN_USER", authorities);
    this.name = name;
    this.mno = mno;
    this.nickname = nickname;
    this.tel = tel;
    this.firstLogin = firstLogin;
    this.email = username;
    this.accounts = accounts;
    this.roles = roles;
    this.status = status;
    this.password = password;
    this.attr = attr;
    this.dto = dto;
  }

  @Override
  public Map<String, Object> getAttributes() {
    return attr;
  }
}
