package com.eeerrorcode.pilllaw.repository.member;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eeerrorcode.pilllaw.entity.member.MemberAddress;

public interface MemberAddressRepository extends JpaRepository<MemberAddress, Long>{
  
}
