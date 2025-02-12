package com.eeerrorcode.pilllaw.repository.order;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eeerrorcode.pilllaw.entity.order.Cart;
import com.eeerrorcode.pilllaw.entity.order.CartItem;
import com.eeerrorcode.pilllaw.entity.product.Product;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
  List<CartItem> findByCartCno(Long cno);
  CartItem findByCartAndProductAndSubday(Cart cart, Product product, long subday);
  void deleteAllByCartCno(Long cno);
}