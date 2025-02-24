package com.eeerrorcode.pilllaw.dto.chat;

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
public class ChatRoomRequestDto {
  private Long creatorId;
  private String lastMessageContent;  
 
}
