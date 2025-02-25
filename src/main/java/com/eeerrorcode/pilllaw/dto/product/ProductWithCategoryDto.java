package com.eeerrorcode.pilllaw.dto.product;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@Builder
@ToString
public class ProductWithCategoryDto {
  
  private ProductDto product;
  private ProductPriceDto productPrice;
  private List<ProductCategoryDto> categories;

  public ProductWithCategoryDto(ProductDto product, ProductPriceDto productPrice, List<ProductCategoryDto> categories) {
    this.product = product;
    this.productPrice = productPrice;
    this.categories = categories;
  }
}
