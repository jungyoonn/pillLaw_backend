package com.eeerrorcode.pilllaw.entity;

import java.time.LocalDateTime;

import org.antlr.v4.runtime.misc.NotNull;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


@Data
@Table(name = "tbl_block")
public class Block {
  @Id
  private Long blockId;

  @NotNull
  private String blocker;
  @NotNull
  private String blocked;
  
  private LocalDateTime blockedAt;
  
   @Column(columnDefinition="TEXT")
  private String reason;
}
