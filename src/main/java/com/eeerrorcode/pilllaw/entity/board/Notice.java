package com.eeerrorcode.pilllaw.entity.board;

import com.eeerrorcode.pilllaw.entity.BaseEntity;
import com.eeerrorcode.pilllaw.entity.member.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_notice")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Notice extends BaseEntity{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long nno;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id", nullable = false)
  private Member member;

  @Column(nullable = false, length = 255)
  private String title;

  @Lob  
  @Column(nullable = false)
  private String content;
  
  @Column(nullable = false)
  private Long count;

  @PrePersist
  public void PrePersistSetCountByZero(){
    this.count = (this.count == null) ? 0 : this.count;
  }

}
