package com.eeerrorcode.pilllaw.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eeerrorcode.pilllaw.entity.Member;


public interface MemberRepository  extends JpaRepository<Member, Long>{
  Member findByEmail(String email);
}
