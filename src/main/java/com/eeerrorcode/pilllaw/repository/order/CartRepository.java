package com.eeerrorcode.pilllaw.repository.order;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eeerrorcode.pilllaw.entity.order.Cart;



public interface CartRepository extends JpaRepository<Cart, Long> {
  Cart findByMemberMno(Long mno);
}