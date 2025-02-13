package com.eeerrorcode.pilllaw.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eeerrorcode.pilllaw.entity.member.Member;


public interface MemberRepository  extends JpaRepository<Member, Long>{
  Optional<Member> findByEmail(String email);
}
