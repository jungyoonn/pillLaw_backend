package com.eeerrorcode.pilllaw.entity.product;

import java.util.*;

import com.eeerrorcode.pilllaw.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_product")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Product extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long pno;

  private String pname;
  private String company;
  private String bestBefore;

  @Column(length = 1000)
  private String effect;

  @Column(length = 1000)
  private String precautions;

  private String keep;
  private boolean state;
  private String href;

  // ✅ 가격 정보: ProductPrice와의 관계 설정 (fetch = FetchType.LAZY 유지)
  // @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
  // private List<ProductPrice> productPrices = new ArrayList<>();

  @Builder.Default
  @ElementCollection(fetch = FetchType.LAZY)
  @CollectionTable(name = "tbl_product_type_set", joinColumns = @JoinColumn(name = "pno"))
  @Enumerated(EnumType.STRING)
  @org.hibernate.annotations.Cascade(org.hibernate.annotations.CascadeType.PERSIST)
  private Set<ProductType> typeSet = new HashSet<>();

  public void addProductType(ProductType pt) {
    typeSet.add(pt);
  }

  // public Long getCurrentPrice() {
  //   if (productPrices.isEmpty()) return null;
  //   return productPrices.get(productPrices.size() - 1).getFinalPrice();
  // }

  // public Integer getCurrentDiscountRate() {
  //   if (productPrices.isEmpty()) return 0;
  //   return productPrices.get(productPrices.size() - 1).getRate();
  // }
}
