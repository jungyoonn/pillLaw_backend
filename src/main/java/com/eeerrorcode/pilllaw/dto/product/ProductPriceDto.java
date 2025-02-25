package com.eeerrorcode.pilllaw.dto.product;

import java.time.LocalDateTime;
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
public class ProductPriceDto {
  
  private Long ppno;

  private Long pno;

  private String price;

  private Long salePrice;

  private Integer rate;

  private LocalDateTime regDate, modDate;

}
