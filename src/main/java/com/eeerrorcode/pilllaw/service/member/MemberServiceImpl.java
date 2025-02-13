package com.eeerrorcode.pilllaw.service.member;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eeerrorcode.pilllaw.dto.member.MemberDto;
import com.eeerrorcode.pilllaw.repository.MemberRepository;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;

@Service
@Transactional
@Log4j2
public class MemberServiceImpl implements MemberService{
  @Autowired
  private MemberRepository repository;

  @Override
  public Optional<MemberDto> get(Long mno) {
    return repository.findById(mno).map(this::toDto);
  }

  @Override
  public MemberDto getByEmail(String email) {
    return toDto(repository.findByEmail(email));
  }

  @Override
  public List<MemberDto> listAll() {
    return repository.findAll().stream().map(this::toDto).toList();
  }

  @Override
  public void modify(MemberDto dto) {
    repository.save(toEntity(dto));    
  }

  @Override
  public Long register(MemberDto dto) {
    if(repository.findByEmail(dto.getEmail()) != null) {
      return null;
    }
    return repository.save(toEntity(dto)).getMno();
  }

  @Override
  public void remove(MemberDto dto) {
    repository.delete(toEntity(dto));    
  }
  
}
