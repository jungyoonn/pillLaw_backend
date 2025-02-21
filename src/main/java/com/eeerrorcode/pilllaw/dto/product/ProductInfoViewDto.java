package com.eeerrorcode.pilllaw.dto.product;

import com.eeerrorcode.pilllaw.entity.product.ProductInfoView;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductInfoViewDto {
  
  private Long pno;
  private String pname;
  private String company;
  private String effect;
  private String precautions;
  private Integer price;
  private Integer salePrice;
  private Double avgRating;
  private Integer reviewLikes;

  public ProductInfoViewDto(ProductInfoView productView) {
  this.pno = productView.getPno();
  this.pname = productView.getPname();
  this.company = productView.getCompany();
  this.effect = productView.getEffect();
  this.precautions = productView.getPrecautions();
  this.price = productView.getPrice();
  this.salePrice = productView.getSalePrice();
  this.avgRating = productView.getAvgRating();
  this.reviewLikes = productView.getReviewLikes();
  }

}
