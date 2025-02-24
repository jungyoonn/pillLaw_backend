package com.eeerrorcode.pilllaw.dto.chat;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomResponseDto {
  private Long chatRoomId;
  private Long creatorId;
  private String lastMessageContent;
  private LocalDateTime lastMessageTime;
  
}
