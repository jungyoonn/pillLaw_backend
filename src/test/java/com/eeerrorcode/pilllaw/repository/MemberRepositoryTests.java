package com.eeerrorcode.pilllaw.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;

import com.eeerrorcode.pilllaw.entity.member.Member;
import com.eeerrorcode.pilllaw.entity.member.MemberAccount;
import com.eeerrorcode.pilllaw.entity.member.MemberRole;
import com.eeerrorcode.pilllaw.entity.member.MemberStatus;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class MemberRepositoryTests {
  @Autowired
  private MemberRepository repository;
  @Autowired
  private PasswordEncoder encoder;

  @Test
  @Transactional
  @Rollback(false)
  public void insertTest() {
    Member member = Member.builder()
      .email("test1@test.com")
      .password(encoder.encode("1234"))
      .name("테스터2")
      .nickname("디그다")
      .tel("010-2222-2222")
      .build();
    member.addMemberAccount(MemberAccount.NORMAL);
    member.addMemberRole(MemberRole.ADMIN);
    member.addMemberRole(MemberRole.USER);
    member.addMemberStatus(MemberStatus.VERIFIED);

    
    repository.save(member);
    log.info(repository.findByEmail(member.getEmail()));
  }

  @Test
  @Transactional
  @Rollback(false)
  public void updateTest() {
    Member member = repository.findByEmail("test@test.com");
    member = Member.builder()
      .mno(member.getMno())
      .email(member.getEmail())
      .password(encoder.encode("1234"))
      .name("이름")
      .nickname("수정된닉네임")
      .tel("010-3123-5634")
      .build();

    member.addMemberRole(MemberRole.SUBSCRIBER);
    repository.save(member);
    log.info(repository.findByEmail("test@test.com"));
  }

  @Test
  @Transactional
  @Rollback(false)
  public void selectListTest() {
    log.info(repository.findAll());
  }

  @Test
  @Transactional
  @Rollback(false)
  public void deleteTest() {
    Member member = repository.findByEmail("test2@test.com");
    repository.delete(member);
    log.info(repository.findAll());
  }
}
