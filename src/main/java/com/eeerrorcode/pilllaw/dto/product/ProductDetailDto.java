package com.eeerrorcode.pilllaw.dto.product;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailDto {
  
  private Long pdno;

  private Long pno;

  private Long mno;

  private String content;

  private Long count;

  private LocalDateTime regDate, modDate;
  
}
