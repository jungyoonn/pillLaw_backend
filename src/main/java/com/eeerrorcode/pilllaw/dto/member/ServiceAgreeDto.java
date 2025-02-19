package com.eeerrorcode.pilllaw.dto.member;

import com.eeerrorcode.pilllaw.entity.member.Member;
import com.eeerrorcode.pilllaw.entity.member.ServiceAgree;
import com.eeerrorcode.pilllaw.repository.MemberRepository;

import jakarta.persistence.EntityManager;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ServiceAgreeDto {
  private Long mno;
  private boolean rule;
  private boolean info;
  private boolean tel;
  private boolean email;

  public ServiceAgree toEntity(EntityManager entityManager, MemberRepository memberRepository) {
    Member member = memberRepository.findById(mno)
      .orElseThrow(() -> new IllegalArgumentException("Member not found: " + mno));

    if (!entityManager.contains(member)) {
      member = entityManager.merge(member); // Member가 detached 상태면 병합
    }

    return ServiceAgree.builder()
      .member(member)
      .info(info)
      .rule(rule)
      .tel(tel)
      .email(email)
      .build();
  }
}
