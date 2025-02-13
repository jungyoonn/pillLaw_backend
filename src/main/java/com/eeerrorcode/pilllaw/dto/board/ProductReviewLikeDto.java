package com.eeerrorcode.pilllaw.dto.board;

import java.time.LocalDateTime;

import com.eeerrorcode.pilllaw.entity.board.ProductReviewLike;
// import com.eeerrorcode.pilllaw.entity.id.ProductReviewLikeId;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductReviewLikeDto {

  // private ProductReviewLikeId id;

  private Long mno;

  private Long prno;

  private LocalDateTime regDate, modDate;

  public ProductReviewLikeDto(ProductReviewLike reviewLike) {
    this.mno = reviewLike.getId().getMno();
    this.prno = reviewLike.getId().getPrno();
    this.regDate = reviewLike.getRegDate();
    this.modDate = reviewLike.getModDate();
  }
}
