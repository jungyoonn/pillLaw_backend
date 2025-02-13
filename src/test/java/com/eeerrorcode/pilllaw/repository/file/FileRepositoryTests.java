package com.eeerrorcode.pilllaw.repository.file;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class FileRepositoryTests {
  
  @Autowired
  FileRepository repository;

  @Test
  public void repoExists(){
    log.info(repository);
  }
}
