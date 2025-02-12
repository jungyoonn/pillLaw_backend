package com.eeerrorcode.pilllaw.entity.board;

import com.eeerrorcode.pilllaw.entity.BaseEntity;
import com.eeerrorcode.pilllaw.entity.member.Member;
import com.eeerrorcode.pilllaw.entity.product.Product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity(name = "tbl_product_review")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ProductReview extends BaseEntity{
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long prno;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "pno", nullable = false)
  private Product product;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "mno", nullable = false)
  private Member member;

  @Lob  
  @Column(nullable = false)
  private String content;

  @Column(nullable = false, columnDefinition = "TINYINT CHECK (rating BETWEEN 1 AND 5)")
  private Integer rating;


  @Column(nullable = false, columnDefinition = "BIGINT DEFAULT 0")
  private Long count;

}
