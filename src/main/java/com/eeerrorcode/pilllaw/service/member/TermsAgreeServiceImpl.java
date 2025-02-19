package com.eeerrorcode.pilllaw.service.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eeerrorcode.pilllaw.dto.member.ServiceAgreeDto;
import com.eeerrorcode.pilllaw.repository.MemberRepository;
import com.eeerrorcode.pilllaw.repository.member.ServiceAgreeRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@Transactional
public class TermsAgreeServiceImpl implements TermsAgreeService{
  @Autowired
  private ServiceAgreeRepository repository;
  @Autowired
  private MemberRepository memberRepository;
  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public Long register(ServiceAgreeDto dto) {
    return repository.save(dto.toEntity(entityManager, memberRepository)).getMno();
  }

  @Override
  public void remove(ServiceAgreeDto dto) {
    repository.deleteById(dto.getMno());
  }
  
}
