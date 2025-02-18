package com.eeerrorcode.pilllaw.repository.member;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.eeerrorcode.pilllaw.entity.member.EmailVerification;

import jakarta.transaction.Transactional;

public interface EmailVerificationRepository extends JpaRepository<EmailVerification, Long>{
  Optional<EmailVerification> findByEmail(String email);
  Optional<EmailVerification> findByToken(String token);

  @Modifying
  @Transactional
  @Query("DELETE FROM tbl_EmailVerification e WHERE e.email = :email")
  void deleteByEmail(@Param("email") String email);
}
