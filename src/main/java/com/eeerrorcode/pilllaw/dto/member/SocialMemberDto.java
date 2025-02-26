package com.eeerrorcode.pilllaw.dto.member;

import com.eeerrorcode.pilllaw.entity.member.Member;
import com.eeerrorcode.pilllaw.entity.member.SocialMember;
import com.eeerrorcode.pilllaw.entity.member.SocialProvider;
import com.eeerrorcode.pilllaw.repository.MemberRepository;

import jakarta.persistence.EntityManager;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SocialMemberDto {
  private String providerId;
  private Long mno;
  private SocialProvider socialProvider;
  private String nickname;

  public SocialMember toEntity(EntityManager entityManager, MemberRepository memberRepository) {
    // mno가 없거나 0인 경우 새로운 Member 생성
    if (mno == null || mno == 0) {
      Member newMember = Member.builder().build();
      newMember = memberRepository.save(newMember);
      mno = newMember.getMno();  // 새로 생성된 id 설정
    }

    Member member = memberRepository.findById(mno)
      .orElseThrow(() -> new IllegalArgumentException("Member not found: " + mno));

    if (!entityManager.contains(member)) {
      member = entityManager.merge(member);
    }

    return SocialMember.builder()
      .providerId(providerId)
      .member(member)
      .socialProvider(socialProvider)
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
