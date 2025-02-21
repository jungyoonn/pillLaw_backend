package com.eeerrorcode.pilllaw.service.chat;

import java.time.LocalDateTime;
import java.util.List;

import com.eeerrorcode.pilllaw.entity.chat.Chatroom;

public interface ChatRoomService {
  List<Chatroom>findByChatrooms(long chatRoomId);
  // void insertChat(long receiver, long sender);

}
