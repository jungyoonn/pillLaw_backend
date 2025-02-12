package com.eeerrorcode.pilllaw.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.eeerrorcode.pilllaw.entity.member.Member;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.Builder;

@Entity
@Table(name = "tbl_chat_participant")
public class ChatParticipant extends BaseEntity {
    @Id
    private Long chatId;
    
    
    private Long memberId;
    private String chatRoomId;
    private LocalDateTime joinedAt;
    private LocalDateTime leftAt;
    private boolean isActive;
    private boolean isMuted;
    
    @Builder.Default
    @ElementCollection(fetch = FetchType.LAZY)
    private Set<InviteStatus> statusSet = new HashSet<>();
    
    @JoinColumn(name = "mno")
    private Member member;
}
