package com.eeerrorcode.pilllaw.entity.order;


import com.eeerrorcode.pilllaw.entity.BaseEntity;
import com.eeerrorcode.pilllaw.entity.product.Product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tbl_cart_item", uniqueConstraints = {
    @UniqueConstraint(columnNames = { "cno", "pno", "subday" }) // Cart 내에서 같은 상품+구독기간이 중복되지 않도록 설정
})
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@ToString(exclude = { "cart", "product" })
public class CartItem {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long cino;

  @ManyToOne
  @JoinColumn(name = "cno", nullable = false)
  private Cart cart;

  @ManyToOne
  @JoinColumn(name = "pno", nullable = false)
  private Product product;
  private Double price;
  private long subday;

  @Builder.Default
  private long quantity = 1L;
  

}