package com.eeerrorcode.pilllaw.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "tbl_chat_room")
public class Chatroom {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long chatRoomId;
  
  private String chreatorId;
  private LocalDateTime chatCreatedAt;
  private LocalDateTime lastMessage;
  private String lastMsgContent;
}
