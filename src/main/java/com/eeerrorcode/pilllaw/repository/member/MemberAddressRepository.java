package com.eeerrorcode.pilllaw.repository.member;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eeerrorcode.pilllaw.entity.member.MemberAddress;

public interface MemberAddressRepository extends JpaRepository<MemberAddress, Long>{
  List<MemberAddress> findByMemberMno(Long mno);
  
}
