package com.eeerrorcode.pilllaw.entity.product;

import com.eeerrorcode.pilllaw.entity.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "tbl_product_price")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ProductPrice extends BaseEntity{
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long ppno;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "pno", nullable = false)
  private Product product;

  private String price; // 정상가

  private Long salePrice; // 할인가

  private Integer rate; // 할인율

  // @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
  // private List<ProductPrice> productPrices = new ArrayList<>();


  // public Long getFinalPrice() {
  //   return salePrice != null ? salePrice : price;
  // }

  // public boolean isDiscounted() {
  //   return salePrice != null && salePrice.compareTo(price) < 0;
  // }

  // public Integer calculateDiscountRate() {
  //   if (isDiscounted()) {
  //     return Math.round((1 - (float) salePrice / price) * 100); // 할인율 계산
  //   }
  //   return 0; // 할인이 없으면 0%
  // }

  // 할인율을 자동 업데이트
  // public void updateRate() {
  //   this.rate = calculateDiscountRate();
  // }
}
