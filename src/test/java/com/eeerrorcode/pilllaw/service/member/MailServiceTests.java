package com.eeerrorcode.pilllaw.service.member;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Transactional
@Log4j2
public class MailServiceTests {
  @Autowired
  private MailService service;

  @Test
  @Rollback(false)
  public void sendVerificationLinkTest() {
    service.sendVerificationLink("sophia76256+100@gmail.com");
  }
}
