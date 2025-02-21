package com.eeerrorcode.pilllaw.service.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.eeerrorcode.pilllaw.repository.chat.ChatroomRepository;

@SpringBootTest
public class ChatRoomServiceTests {
  @Autowired
  private ChatRoomService service;
  @Autowired
  private ChatroomRepository repository;


  // @Test  void insertChat(long receiver, long sender);

  
}
