package com.eeerrorcode.pilllaw.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.eeerrorcode.pilllaw.entity.member.Member;
import com.eeerrorcode.pilllaw.entity.member.MemberAccount;


public interface MemberRepository  extends JpaRepository<Member, Long>{
  Optional<Member> findByEmail(String email);

  @Query("SELECT m FROM tbl_member m WHERE m.email = :email AND :accountType MEMBER OF m.accountSet")
  Optional<Member> findByEmailAndAccountType(@Param("email") String email, @Param("accountType") MemberAccount accountType);
}
