package com.eeerrorcode.pilllaw.dto.follow;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FollowDto {
  private long followId;

  private LocalDateTime createdAt;
  private boolean IsFollowBack;
  private long receiverFollowId;
  private long senderFollowId;
  private LocalDateTime moddate;
  private LocalDateTime regdate;
  
}
