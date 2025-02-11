package com.eeerrorcode.pilllaw.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;
import lombok.*;

@MappedSuperclass
@EntityListeners(value = AuditingEntityListener.class)
@Getter
@ToString
public class BaseEntity {
  @CreatedDate
  @Column(name = "regdate", updatable = false)
  private LocalDateTime regDate;

  @LastModifiedDate
  @Column(name = "moddate")
  private LocalDateTime modDate;  
}
