package com.eeerrorcode.pilllaw.repository.letter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class LetterRepositoryTest {
  @Autowired
  LetterRepository repository;

  @Test
  @Transactional
  @Rollback(false)
  public void listTest() {
    // repository.findByReceiverId(2L);
    
  }
}
