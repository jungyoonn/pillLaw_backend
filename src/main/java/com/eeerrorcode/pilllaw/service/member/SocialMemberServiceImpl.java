package com.eeerrorcode.pilllaw.service.member;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eeerrorcode.pilllaw.dto.member.SocialMemberDto;
import com.eeerrorcode.pilllaw.entity.member.SocialProvider;
import com.eeerrorcode.pilllaw.repository.MemberRepository;
import com.eeerrorcode.pilllaw.repository.member.SocialMemberRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@Transactional
public class SocialMemberServiceImpl implements SocialMemberService{
  @Autowired
  private SocialMemberRepository repository;
  @Autowired
  private MemberRepository memberRepository;
  @PersistenceContext
  private EntityManager entityManager;
  
  @Override
  public String register(SocialMemberDto dto) {
    return repository.save(dto.toEntity(entityManager, memberRepository)).getProviderId();
  }

  @Override
  public Optional<SocialMemberDto> getByProviderIdAndProvider(String providerId, SocialProvider provider) {
    return repository.findByProviderIdAndSocialProvider(providerId, provider).isPresent() ?
      toOptionalDto(repository.findByProviderIdAndSocialProvider(providerId, provider).get()) : null;
  }

  @Override
  public Optional<SocialMemberDto> getByProviderId(String providerId) {
    return repository.findByProviderId(providerId).isPresent() ?
      toOptionalDto(repository.findByProviderId(providerId).get()) : null;
  }

  @Override
  public Optional<SocialMemberDto> getByMno(Long mno) {
    return repository.findByMno(mno).isPresent() ? 
      toOptionalDto(repository.findByMno(mno).get()) : Optional.empty();
  }
  
}
