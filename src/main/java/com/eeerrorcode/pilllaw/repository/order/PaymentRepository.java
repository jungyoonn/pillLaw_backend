package com.eeerrorcode.pilllaw.repository.order;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eeerrorcode.pilllaw.entity.order.Payment;


public interface PaymentRepository extends JpaRepository<Payment, Long> {
}