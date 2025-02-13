package com.eeerrorcode.pilllaw.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eeerrorcode.pilllaw.entity.member.Member;
import com.eeerrorcode.pilllaw.entity.member.MemberAccount;


public interface MemberRepository  extends JpaRepository<Member, Long>{
  Optional<Member> findByEmail(String email);
  Optional<Member> findByEmailAndAccountSet(String email, Set<MemberAccount> accounts);
}
