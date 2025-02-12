package com.eeerrorcode.pilllaw.entity.board;

import com.eeerrorcode.pilllaw.entity.BaseEntity;
import com.eeerrorcode.pilllaw.entity.id.ProductReviewLikeId;
import com.eeerrorcode.pilllaw.entity.member.Member;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "tbl_review_like")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ProductReviewLike extends BaseEntity{

  @EmbeddedId  
  private ProductReviewLikeId id;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("mno")  
  @JoinColumn(name = "mno", nullable = false)
  private Member member;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("prno")  
  @JoinColumn(name = "prno", nullable = false)
  private ProductReview productReview;
  
}
