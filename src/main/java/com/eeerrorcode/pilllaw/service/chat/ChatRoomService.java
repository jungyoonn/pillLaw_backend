package com.eeerrorcode.pilllaw.service.chat;

import java.time.LocalDateTime;
import java.util.List;

import com.eeerrorcode.pilllaw.dto.chat.ChatRoomRequestDto;
import com.eeerrorcode.pilllaw.dto.chat.ChatRoomResponseDto;
import com.eeerrorcode.pilllaw.entity.chat.Chatroom;
import com.eeerrorcode.pilllaw.entity.member.Member;

public interface ChatRoomService {
  List<Chatroom> findByChatrooms(long chatRoomId);
  // void insertChat(long receiver, long sender);
  Chatroom saveChatroom(Chatroom chatroom);



  // 2025-02-24 오후에 Dto추가건/재 변경필요로 주석
  // List<ChatRoomResponseDto> getAllChatrooms(Long mno);
  // ChatRoomResponseDto createChatroom(ChatRoomRequestDto requestDto);
  // List<ChatRoomResponseDto> getChatRooms();

}