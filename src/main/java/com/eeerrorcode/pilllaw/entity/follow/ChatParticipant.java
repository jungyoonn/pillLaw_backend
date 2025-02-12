package com.eeerrorcode.pilllaw.entity.follow;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.eeerrorcode.pilllaw.entity.BaseEntity;
import com.eeerrorcode.pilllaw.entity.follow.InviteStatus;
import com.eeerrorcode.pilllaw.entity.member.Member;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tbl_chat_participant")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ChatParticipant extends BaseEntity {
    @Id
    private Long chatId;
    
    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "chat_room_id", nullable = false)
    private Chatroom chatRoom;

    @CreationTimestamp
    private LocalDateTime joinedAt;

    private LocalDateTime leftAt;
    @Builder.Default
    private boolean isActive = true;
    
    @Builder.Default
    private boolean isMuted = false;

    @Enumerated(EnumType.STRING)
    private InviteStatus inviteStatus;
}

// enum InviteStatus { PENDING, ACCEPTED, DECLINED }
