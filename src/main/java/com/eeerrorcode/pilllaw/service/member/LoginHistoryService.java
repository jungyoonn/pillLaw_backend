package com.eeerrorcode.pilllaw.service.member;

import java.util.List;

import com.eeerrorcode.pilllaw.dto.member.LoginHistoryDto;
import com.eeerrorcode.pilllaw.entity.member.LoginHistory;
import com.eeerrorcode.pilllaw.entity.member.Member;

public interface LoginHistoryService {
  Long register(LoginHistoryDto dto);

  LoginHistoryDto get(Long mno);

  List<LoginHistoryDto> getListByEmail(String email);

  default LoginHistoryDto toDto(LoginHistory history) {
    LoginHistoryDto dto = LoginHistoryDto.builder()
      .lno(history.getLno())
      .loginTime(history.getLoginTime())
      .ip(history.getIp())
      .device(history.getDevice())
      .loginResult(history.getLoginResult())
      .failureReason(history.getFailureReason())
      .mno(history.getMember().getMno())
      .build();
    
    return dto;
  }

  default LoginHistory toEntity(LoginHistoryDto dto) {
    LoginHistory history = LoginHistory.builder()
      .lno(dto.getLno())
      .loginTime(dto.getLoginTime())
      .ip(dto.getIp())
      .device(dto.getDevice())
      .loginResult(dto.getLoginResult())
      .failureReason(dto.getFailureReason())
      .member(dto.getMno() == null ? null : Member.builder().mno(dto.getMno()).build())
      .build();

    return history;
  }
}
