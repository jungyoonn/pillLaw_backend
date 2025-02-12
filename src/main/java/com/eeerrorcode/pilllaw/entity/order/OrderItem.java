package com.eeerrorcode.pilllaw.entity.order;


import com.eeerrorcode.pilllaw.entity.Product;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "tbl_order_item")
@Getter
@Setter
public class OrderItem {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long oino;

  @ManyToOne
  @JoinColumn(name = "ono", nullable = false)
  private Order order;

  @ManyToOne
  @JoinColumn(name = "pno", nullable = false)
  private Product product;

  private long price;
  private long subday;
  private long quantity;
}