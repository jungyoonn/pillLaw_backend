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
@AllArgsConstructor
@Builder
@ToString
public class ProductWithCategoryDto {
  
  private ProductDto product;
  private List<ProductCategoryDto> categories;
}
