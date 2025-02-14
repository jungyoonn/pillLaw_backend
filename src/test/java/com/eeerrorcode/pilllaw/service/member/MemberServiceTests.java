package com.eeerrorcode.pilllaw.service.member;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;

import com.eeerrorcode.pilllaw.dto.member.MemberDto;
import com.eeerrorcode.pilllaw.entity.member.MemberAccount;
import com.eeerrorcode.pilllaw.entity.member.MemberRole;
import com.eeerrorcode.pilllaw.entity.member.MemberStatus;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class MemberServiceTests {
  @Autowired
  private MemberService service;
  @Autowired
  private PasswordEncoder encoder;

  @Test
  @Transactional
  @Rollback(false)
  public void insertTest() {
    MemberDto dto = MemberDto.builder()
      .email("modTest@test.com")
      .password(encoder.encode("1234"))
      .name("삭제 테스트")
      .nickname("삭제될 예정임")
      .tel("010-0000-0000")
      .build();

    List<MemberAccount> accounts = new ArrayList<>();
    List<MemberRole> roles = new ArrayList<>();
    List<MemberStatus> status = new ArrayList<>();

    accounts.add(MemberAccount.NORMAL);
    roles.add(MemberRole.ADMIN);
    roles.add(MemberRole.USER);
    roles.add(MemberRole.SUBSCRIBER);
    status.add(MemberStatus.VERIFIED);

    dto.setAccounts(accounts);
    dto.setRoles(roles);
    dto.setStatus(status);

    service.register(dto);
  }

  @Test
  public void selectOneTest() {
    log.info(service.get(6L));
  }

  @Test
  public void getByEmailTest() {
    log.info(service.getByEmail("service@test.com"));
  }

  @Test
  public void selectListTest() {
    log.info(service.listAll());
  }
  
  @Test
  public void modifyTest() {
    MemberDto dto = service.getByEmail("modTest@test.com").orElse(null);

    dto.setName("유비빔");
    dto.setNickname("비빔박자");

    List<MemberRole> roles = new ArrayList<>();
    roles.add(MemberRole.USER);

    dto.setRoles(roles);

    service.modify(dto);
  }

  @Test
  public void removeTest() {
    MemberDto dto = service.getByEmail("delTest@test.com").orElse(null);

    service.remove(dto);
  }

  @Test
  public void loginTest() {
    log.info(service.login("modTest@test.com", "1"));
  }
}
