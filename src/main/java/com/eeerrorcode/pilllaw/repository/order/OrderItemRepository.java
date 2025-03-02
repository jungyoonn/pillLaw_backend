package com.eeerrorcode.pilllaw.repository.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.eeerrorcode.pilllaw.entity.order.OrderItem;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
  List<OrderItem> findByOrderOno(Long ono);

  @Query("SELECT o.product.pno FROM tbl_order_item o GROUP BY o.product.pno ORDER BY COUNT(o.product.pno) DESC LIMIT 6")
  List<Long> findTopOrderedProducts();
}