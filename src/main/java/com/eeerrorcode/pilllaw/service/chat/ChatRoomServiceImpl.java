package com.eeerrorcode.pilllaw.service.chat;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eeerrorcode.pilllaw.entity.chat.Chatroom;
import com.eeerrorcode.pilllaw.repository.chat.ChatroomRepository;

@Service
public class ChatRoomServiceImpl implements ChatRoomService {
  
  private final ChatroomRepository repository;

  // 생성자 주입
  public ChatRoomServiceImpl(ChatroomRepository repository) {
    this.repository = repository;
  }

  @Override
  public List<Chatroom> findByChatrooms(long chatRoomId) {
    return repository.findByChatRoomId(chatRoomId);  // repository를 통해 데이터 조회
  }
  // 새로운 채팅방을 저장하는 메서드
  public Chatroom saveChatroom(Chatroom chatroom) {
    return repository.save(chatroom);  // JPA의 save() 메서드를 사용하여 저장
  }

}
