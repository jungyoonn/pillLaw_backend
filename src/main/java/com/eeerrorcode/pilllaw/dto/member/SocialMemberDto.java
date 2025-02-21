package com.eeerrorcode.pilllaw.dto.member;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.eeerrorcode.pilllaw.entity.member.Member;
import com.eeerrorcode.pilllaw.entity.member.SocialMember;
import com.eeerrorcode.pilllaw.entity.member.SocialProvider;
import com.eeerrorcode.pilllaw.repository.MemberRepository;

import jakarta.persistence.EntityManager;
import lombok.*;
import lombok.Builder.Default;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SocialMemberDto {
  private String providerId;
  private Long mno;

  @Default
  private List<SocialProvider> providers = new ArrayList<>();

  public void addProvider(SocialProvider provider) {
    if(this.providers == null) {
      this.providers = new ArrayList<>();
    }
    this.providers.add(provider);
  }

  public SocialMember toEntity(EntityManager entityManager, MemberRepository memberRepository) {
    Member member = memberRepository.findById(mno)
      .orElseThrow(() -> new IllegalArgumentException("Member not found: " + mno));
  
    if (!entityManager.contains(member)) {
      member = entityManager.merge(member); // Member가 detached 상태면 병합
    }
    
    return SocialMember.builder()
      .providerId(providerId)
      .member(Member.builder().mno(mno).build())
      .socialProviders(new HashSet<>(providers))
      .build();
  }
}
