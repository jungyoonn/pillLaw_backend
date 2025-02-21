package com.eeerrorcode.pilllaw.service.member;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eeerrorcode.pilllaw.dto.member.SocialMemberDto;
import com.eeerrorcode.pilllaw.entity.member.SocialProvider;
import com.eeerrorcode.pilllaw.repository.member.SocialMemberRepository;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@Transactional
public class SocialMemberServiceImpl implements SocialMemberService{
  @Autowired
  private SocialMemberRepository repository;
  
  @Override
  public String register(SocialMemberDto dto) {
    return repository.save(toEntity(dto)).getProviderId();
  }

  @Override
  public Optional<SocialMemberDto> getByProviderIdAndProvider(String providerId, SocialProvider provider) {
    return repository.findByProviderIdAndProviders(providerId, provider).isPresent() ?
      toOptionalDto(repository.findByProviderIdAndProviders(providerId, provider).get()) : null;
  }
  
}
