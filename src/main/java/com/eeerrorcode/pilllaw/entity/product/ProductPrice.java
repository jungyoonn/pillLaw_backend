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
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProductPrice extends BaseEntity{
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long ppno;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "pno", nullable = false)
  private Product product;

  private Long price; // 정상가

  private Long salePrice; // 할인가

}
