package com.eeerrorcode.pilllaw.dto.product;

import java.util.List;

import com.eeerrorcode.pilllaw.dto.board.ProductReviewDto;

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
  private List<ProductReviewDto> reviews;

  public ProductWithCategoryDto(ProductDto product, ProductPriceDto productPrice, List<ProductCategoryDto> categories, List<ProductReviewDto> reviews) {
    this.product = product;
    this.productPrice = productPrice;
    this.categories = categories;
    this.reviews = reviews;
  }
}
