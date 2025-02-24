package com.eeerrorcode.pilllaw.service.chat;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eeerrorcode.pilllaw.dto.chat.ChatRoomRequestDto;
import com.eeerrorcode.pilllaw.dto.chat.ChatRoomResponseDto;
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




  // @Override
  // public ChatRoomResponseDto createChatRoom(ChatRoomRequestDto requestDto) {
  //     // 새로운 채팅방을 생성
  //     Chatroom chatroom = new Chatroom();
  //     chatroom.setCreatorId(requestDto.getCreatorId());
  //     chatroom.setLastMessageContent(requestDto.getLastMessageContent());
  //     chatroom = repository.save(chatroom);

  //     // Response DTO를 반환
  //     return new ChatRoomResponseDto(
  //       chatroom.getChatRoomId(),
  //       chatroom.getCreator().getMno(),  // Member의 mno
  //       chatroom.getLastMessageContent(), // lastMessageContent 필드 사용
  //       chatroom.getChatCreatedAt(),
  //       chatroom.getLastMessage()
  //   );
  // }

  // @Override
  // public List<ChatRoomResponseDto> getChatRooms() {
  //     List<Chatroom> chatrooms = repository.findAll();
  //     return chatrooms.stream()
  //         .map(chatroom -> new ChatRoomResponseDto(
  //             chatroom.getChatRoomId(),
  //             chatroom.getCreator().getMno(),  // Member의 mno를 사용
  //             chatroom.getLastMsgContent(),
  //             chatroom.getChatCreatedAt(),
  //             chatroom.getLastMessage()
  //         ))
  //         .collect(Collectors.toList());
  // }





}
