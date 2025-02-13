package com.eeerrorcode.pilllaw.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class SecurityTests {
  @Autowired
  private PasswordEncoder encoder;

  @Test
  public void encoderTest() {
    String password = "1234";
    String encoded = encoder.encode(password);

    log.info("password ::: " + password);
    log.info("encoded password ::: " + encoded);

    
  }
}
