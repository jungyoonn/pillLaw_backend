package com.eeerrorcode.pilllaw.service.member;

import com.eeerrorcode.pilllaw.dto.member.ServiceAgreeDto;
import com.eeerrorcode.pilllaw.entity.member.ServiceAgree;

public interface TermsAgreeService {
  Long register(ServiceAgreeDto dto);

  void remove(ServiceAgreeDto dto);

  // default ServiceAgree toEntity(ServiceAgreeDto dto) {

  //   ServiceAgree serviceAgree = ServiceAgree.builder()
  //     .member(Member.builder().mno(dto.getMno()).build())
  //     // .mno(dto.getMno())
  //     .info(dto.isInfo())
  //     .rule(dto.isRule())
  //     .tel(dto.isTel())
  //     .email(dto.isEmail())
  //     .build();

  //   return serviceAgree;
  // }

  default ServiceAgreeDto toDto(ServiceAgree serviceAgree) {
    ServiceAgreeDto dto = ServiceAgreeDto.builder()
      .mno(serviceAgree.getMno())
      .info(serviceAgree.isInfo())
      .rule(serviceAgree.isRule())
      .tel(serviceAgree.isTel())
      .email(serviceAgree.isEmail())
      .build();

    return dto;
  }
}
