package com.eeerrorcode.pilllaw.repository.member;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.eeerrorcode.pilllaw.entity.member.MemberAddress;

public interface MemberAddressRepository extends JpaRepository<MemberAddress, Long> {
  List<MemberAddress> findByMemberMno(Long mno);

  Optional<MemberAddress> findByMember_MnoAndPostalCodeAndRoadAddressAndDetailAddressAndTel(
      Long mno, String postalCode, String roadAddress, String detailAddress, String tel);
      
  // Optional<MemberAddress>
  // findByMember_IdAndPostalCodeAndRoadAddressAndDetailAddressAndTel(Long mno,
  // String postalCode, String roadAddress, String detailAddress, String tel);

  @Query("SELECT a FROM tbl_member_address a WHERE a.member.mno = :mno AND a.defaultAddr = :defaultAddr")
  Optional<MemberAddress> findByMnoAndDefaultAddr(@Param("mno") Long mno, @Param("defaultAddr") boolean defaultAddr);

  @Query("SELECT a FROM tbl_member_address a WHERE a.member.mno = :mno")
  List<MemberAddress> findByMno(@Param("mno") Long mno);
}
