package com.eeerrorcode.pilllaw.repository.chat;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eeerrorcode.pilllaw.dto.chat.ChatRoomRequestDto;
import com.eeerrorcode.pilllaw.dto.chat.ChatRoomResponseDto;
import com.eeerrorcode.pilllaw.entity.chat.Chatroom;

@Repository
public interface ChatroomRepository extends JpaRepository <Chatroom, Long>{
  List<Chatroom> findByChatRoomId(long chatRoomId);
  // ChatRoomResponseDto createChatroom(ChatRoomRequestDto requestDto);
  // List<ChatRoomResponseDto> getChatRooms();

}
