package com.eeerrorcode.pilllaw.repository.follow;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.eeerrorcode.pilllaw.entity.follow.Follow;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class FollowRepositoryTests {
  @Autowired
  private FollowRepository repository;

  @Test
  public void testExist(){
    log.info(repository);
  }
  @Test
  public void test() {
    // List<Follow>returnList = 
    log.info(repository.findByFollowIdAndSenderMno(13L, 12L));
  }
  @Test
  public void testIsBack(){
    log.info(repository.findByIsFollowBack(false));
  }
  @Test
  public void testreceiverFollow(){
    log.info(repository.findByReceiver_Mno(13L));
  }

  // @Test
  // public void testbooleanFollow(){
  //   log.info(repository.existsBySender_MnoAndReceiver_Mno(6, 3));
  // }
  @Test
  public void testIsBackFollow(){
    log.info(repository.findBySender_MnoAndReceiver_Mno(42L, 40L));
  }
}
