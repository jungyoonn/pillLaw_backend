package com.eeerrorcode.pilllaw.dto.chat;

import com.eeerrorcode.pilllaw.entity.member.Member;

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
public class ChatParticipantRequestDto {
  private Long chatRoomId;
  private Member mno;
  private String content;
  
}