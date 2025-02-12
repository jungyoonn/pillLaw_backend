package com.eeerrorcode.pilllaw.service.member;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import com.eeerrorcode.pilllaw.dto.member.MemberDto;
import com.eeerrorcode.pilllaw.entity.member.Member;

public interface MemberService {
  Long register(MemberDto dto);

  void modify(MemberDto dto);

  void remove(MemberDto dto);

  Optional<MemberDto> get(Long mno);

  MemberDto getByEmail(String email);

  List<MemberDto> listAll();

  default Member toEntity(MemberDto dto) {
    Member member = Member.builder()
      .mno(dto.getMno())
      .email(dto.getEmail())
      .password(dto.getPassword())
      .name(dto.getName())
      .nickname(dto.getNickname())
      .tel(dto.getTel())
      .firstLogin(dto.isFirstLogin())
      .roleSet(new HashSet<>(dto.getRoles()))
      .accountSet(new HashSet<>(dto.getAccounts()))
      .statusSet(new HashSet<>(dto.getStatus()))
      .build();

    return member;
  }

  default MemberDto toDto(Member member) {
    MemberDto dto = MemberDto.builder()
      .mno(member.getMno())
      .email(member.getEmail())
      .password(member.getPassword())
      .name(member.getName())
      .nickname(member.getNickname())
      .tel(member.getTel())
      .firstLogin(member.isFirstLogin())
      .roles(new ArrayList<>(member.getRoleSet()))
      .accounts(new ArrayList<>(member.getAccountSet()))
      .status(new ArrayList<>(member.getStatusSet()))
      .build();

    return dto;
  }
}
