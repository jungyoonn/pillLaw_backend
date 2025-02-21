package com.eeerrorcode.pilllaw.service.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eeerrorcode.pilllaw.dto.member.LoginHistoryDto;
import com.eeerrorcode.pilllaw.repository.member.LoginHistoryRepository;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@Transactional
public class LoginHistoryServiceImpl implements LoginHistoryService{
  @Autowired
  private LoginHistoryRepository historyRepository;
  
  @Override
  public Long register(LoginHistoryDto dto) {
    return historyRepository.save(toEntity(dto)).getLno();
  }

  @Override
  public LoginHistoryDto get(Long mno) {
    throw new UnsupportedOperationException("Unimplemented method 'get'");
  }

  @Override
  public List<LoginHistoryDto> getListByEmail(String email) {
    throw new UnsupportedOperationException("Unimplemented method 'getListByEmail'");
  }
  
}
