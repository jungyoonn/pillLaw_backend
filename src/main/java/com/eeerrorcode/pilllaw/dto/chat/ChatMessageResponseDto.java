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
public class ChatMessageResponseDto {
  private Long messageId;
  private Long chatRoomId;
  private Long senderId;
  private String content;
  private LocalDateTime sentAt;
  private boolean isDeleted;
  
}
