package com.pilllaw.pilllaw.entity.order;

import com.pilllaw.pilllaw.entity.product.Product;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "tbl_cart_item")
@Getter
@Setter
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

  private long subday;
  private long quantity;
}