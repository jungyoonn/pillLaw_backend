package com.eeerrorcode.pilllaw.repository.product;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class CategoryRepositoryTests {
  
  @Autowired
  CategoryRepository repository;

  @Test
  public void repoExists(){
    log.info(repository);
  }
}
