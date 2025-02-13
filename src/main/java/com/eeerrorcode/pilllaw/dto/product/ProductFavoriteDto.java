package com.eeerrorcode.pilllaw.dto.product;

import java.time.LocalDateTime;

import com.eeerrorcode.pilllaw.entity.id.ProductFavoriteId;

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
public class ProductFavoriteDto {
  
  // private ProductFavoriteId id;

  private Long mno;

  private Long pno;

  private LocalDateTime regDate, modDate;

  public ProductFavoriteDto(ProductFavoriteId id, LocalDateTime regDate, LocalDateTime modDate) {
    this.mno = id.getMno();
    this.pno = id.getPno();
    this.regDate = regDate;
    this.modDate = modDate;
  }
}
