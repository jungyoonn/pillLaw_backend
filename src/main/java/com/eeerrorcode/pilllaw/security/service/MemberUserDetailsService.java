package com.eeerrorcode.pilllaw.security.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.eeerrorcode.pilllaw.dto.member.MemberDto;
import com.eeerrorcode.pilllaw.entity.member.MemberAccount;
import com.eeerrorcode.pilllaw.repository.MemberRepository;
import com.eeerrorcode.pilllaw.security.dto.AuthMemberDto;
import com.eeerrorcode.pilllaw.service.member.MemberService;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class MemberUserDetailsService implements UserDetailsService{
  @Autowired
  private MemberRepository repository;

  @Autowired
  private MemberService memberService;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<MemberDto> optional = memberService.toOptionalDto(repository.findByEmailAndAccountType(username
    , MemberAccount.NORMAL).orElse(null));
  
    if(optional.isEmpty()) {
      throw new UsernameNotFoundException(username);
    }
    MemberDto dto = optional.get();

    AuthMemberDto authMemberDto = new AuthMemberDto(dto.getEmail(), dto.getPassword(), dto.getMno(), dto.getAccounts()
    , dto.getName(), dto.getNickname(), dto.getTel(), dto.isFirstLogin()
    , dto.getRoles().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.name())).toList());

    return authMemberDto;
  }
  
}
