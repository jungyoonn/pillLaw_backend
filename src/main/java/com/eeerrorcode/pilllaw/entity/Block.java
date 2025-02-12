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
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_block")
@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Block {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long blockId;

  @ManyToOne
  @JoinColumn(name = "blocker", nullable = false)
  private Member blocker;

  @ManyToOne
  @JoinColumn(name = "blocked", nullable = false)
  private Member blocked;

  @CreationTimestamp
  private LocalDateTime blockedAt;

  @Column(columnDefinition = "TEXT")
  private String reason;
  
}
