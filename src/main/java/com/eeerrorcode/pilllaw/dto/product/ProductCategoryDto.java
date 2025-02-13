package com.eeerrorcode.pilllaw.dto.product;

import com.eeerrorcode.pilllaw.entity.product.ProductCategory;

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
public class ProductCategoryDto {
  
  private Long pno;
  private Long cno;
  private String cname;

  public ProductCategoryDto(ProductCategory productCategory) {
  this.pno = productCategory.getProduct().getPno();
  this.cno = productCategory.getCategory().getCno();
  this.cname = (productCategory.getCategory().getCname() != null) ? productCategory.getCategory().getCname() : "Unknown";
  }
}
