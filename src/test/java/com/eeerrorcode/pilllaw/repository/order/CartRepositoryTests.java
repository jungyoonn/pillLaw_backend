package com.eeerrorcode.pilllaw.repository.order;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.eeerrorcode.pilllaw.entity.order.Cart;
import com.eeerrorcode.pilllaw.repository.MemberRepository;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
@Transactional
public class CartRepositoryTests {
  @Autowired
  private CartRepository cartRepository;

  @Test
  @Rollback(false)
  public void testInsert() {
    

  }
}


