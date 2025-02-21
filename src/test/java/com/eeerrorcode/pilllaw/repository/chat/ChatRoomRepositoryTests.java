package com.eeerrorcode.pilllaw.repository.chat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import jakarta.transaction.Transactional;
import lombok.Data;

@SpringBootTest
public class ChatRoomRepositoryTests {

  @Autowired
  private ChatroomRepository repository;

  @Test
  @Transactional
  @Rollback(false)
  public void ChatroomTest() {
    repository.findByChatRoomId(1);  
    // return null;
  }
}
