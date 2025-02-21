package com.eeerrorcode.pilllaw.entity.chat;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.eeerrorcode.pilllaw.entity.BaseEntity;
import com.eeerrorcode.pilllaw.entity.member.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
// import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
// @Data
@Table(name = "tbl_chat_room")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Chatroom extends BaseEntity{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long chatRoomId;
  
    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false)
    private Member creator;

    @CreationTimestamp
    private LocalDateTime chatCreatedAt;

    private LocalDateTime lastMessage;

    @Column(length = 250)
    private String lastMsgContent;
}
