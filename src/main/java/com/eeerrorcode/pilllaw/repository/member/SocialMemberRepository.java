package com.eeerrorcode.pilllaw.repository.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.eeerrorcode.pilllaw.entity.member.SocialMember;
import com.eeerrorcode.pilllaw.entity.member.SocialProvider;

import java.util.Optional;


public interface SocialMemberRepository extends JpaRepository<SocialMember, String>{
  @Query("SELECT s FROM tbl_social_member s WHERE s.providerId = :providerId AND :provider MEMBER OF s.socialProviders")
  Optional<SocialMember> findByProviderIdAndProviders(@Param("providerId") String providerId, @Param("provider") SocialProvider provider);
}
