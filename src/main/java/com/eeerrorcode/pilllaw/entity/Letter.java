package com.eeerrorcode.pilllaw.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

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
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Data
@Table(name = "tbl_letter")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Letter extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long letterId;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private Member sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private Member receiver;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @CreationTimestamp
    private LocalDateTime sentAt;

    private LocalDateTime readAt;

    private boolean deletedBySender;
    private boolean deletedByReceiver;
    
}
