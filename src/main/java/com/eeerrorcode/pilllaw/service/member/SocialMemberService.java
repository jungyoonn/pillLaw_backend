package com.eeerrorcode.pilllaw.service.member;

import java.util.Optional;

import com.eeerrorcode.pilllaw.dto.member.SocialMemberDto;
import com.eeerrorcode.pilllaw.entity.member.Member;
import com.eeerrorcode.pilllaw.entity.member.SocialMember;
import com.eeerrorcode.pilllaw.entity.member.SocialProvider;

public interface SocialMemberService {
  String register(SocialMemberDto dto);

  Optional<SocialMemberDto> getByProviderIdAndProvider(String providerId, SocialProvider provider);

  Optional<SocialMemberDto> getByProviderId(String providerId);

  Optional<SocialMemberDto> getByMno(Long mno);

  default SocialMember toEntity(SocialMemberDto dto) {
    SocialMember member = SocialMember.builder()
      .providerId(dto.getProviderId())
      .member(Member.builder().mno(dto.getMno()).build())
      .socialProvider(dto.getSocialProvider())
      .build();

    return member;
  }

  default SocialMemberDto toDto(SocialMember member) {
    SocialMemberDto dto = SocialMemberDto.builder()
      .providerId(member.getProviderId())
      .mno(member.getMember().getMno())
      .socialProvider(member.getSocialProvider())
      .build();

    return dto;
  }

  default Optional<SocialMemberDto> toOptionalDto(SocialMember member) {
    if(member == null) {
      return Optional.empty();
    }

    SocialMemberDto dto = SocialMemberDto.builder()
      .providerId(member.getProviderId())
      .mno(member.getMember().getMno())
      .socialProvider(member.getSocialProvider())
      .build();

    return Optional.of(dto);
  }
}
