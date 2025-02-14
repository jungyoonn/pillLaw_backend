package com.eeerrorcode.pilllaw.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.eeerrorcode.pilllaw.security.util.JWTUtil;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class JWTTests {
  @Autowired
  private JWTUtil util;

  @Test
  public void testEncode() throws Exception {
    String email = "service@test.com";
    String str = util.generateToken(email);
    log.info(str);
  }

  @Test
  public void testValidate() throws Exception {
    String email = "service@test.com";
    String str = util.generateToken(email);

    Thread.sleep(5000);

    String resultEmail = util.validateAndExtract(str);

    log.info(resultEmail);
  }
}