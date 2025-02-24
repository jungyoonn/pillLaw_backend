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
    Member member;
    
    if (mno != null) {
      // 기존 회원인 경우
      member = memberRepository.findById(mno)
        .orElseThrow(() -> new IllegalArgumentException("Member not found: " + mno));
      
      if (!entityManager.contains(member)) {
        member = entityManager.merge(member);
      }
    } else {
      // 새로운 회원인 경우
      member = Member.builder().build(); // 기본 Member 생성
      member = memberRepository.save(member); // 먼저 Member 저장하여 ID 생성
      mno = member.getMno(); // 생성된 ID 설정
    }

    return SocialMember.builder()
      .providerId(providerId)
      .member(member) // 새로 Member 객체를 생성하지 않고 조회/생성된 Member 사용
      .socialProviders(new HashSet<>(providers))
      .build();

    // Member member = memberRepository.findById(mno)
    //   .orElseThrow(() -> new IllegalArgumentException("Member not found: " + mno));
  
    // if (!entityManager.contains(member)) {
    //   member = entityManager.merge(member); // Member가 detached 상태면 병합
    // }
    
    // return SocialMember.builder()
    //   .providerId(providerId)
    //   .member(Member.builder().mno(mno).build())
    //   .socialProviders(new HashSet<>(providers))
    //   .build();
  }
}
